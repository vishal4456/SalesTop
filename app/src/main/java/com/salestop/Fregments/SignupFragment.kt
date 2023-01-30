package com.salestop.Fregments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.salestop.*
import com.salestop.Ext.nameVailded
import com.salestop.Model.LoginVM
import com.salestop.base.BaseFragment
import com.salestop.databinding.FragmentSignupBinding
import java.util.*


class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private lateinit var database: DatabaseReference
    private lateinit var vm : LoginVM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm = ViewModelProvider(this)[LoginVM::class.java]
        onClick()
        onBackPressed()
        setObserver()

    }

    fun setObserver() {
        vm.msg.observe(viewLifecycleOwner){
        Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        }
    }


    override fun initView() {
        super.initView()
        onBackPressed()
    }


    private fun getGender(): String {
        val checked = binding.rgGender.checkedRadioButtonId
        val gender = view?.findViewById<RadioButton>(checked)
        return gender?.text.toString()

    }

    override fun onClick() {
        binding.etDob.setOnClickListener {
            //  Toast.makeText(context,getGender(),Toast.LENGTH_SHORT).show()
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dataPickerDialog = DatePickerDialog(
                requireContext(),

                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.

                    binding.etDob.text =
                            // ("$dayOfMonth.toString()"+("${monthOfYear.toString()}+1")+"$year")
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            dataPickerDialog.show()
        }
        binding.btSubmit.setOnClickListener {
            if (isVaild()) {
                val profile = Profile(
                    binding.etFirstname.text.toString(),
                    binding.etLastname.text.toString(),
                    binding.etUsername.text.toString(),
                    binding.etDob.text.toString(),
                    getGender(),
                    binding.etPassword.text.toString(),
                )

                 vm.signup(profile)
            }

            //    sendData()
        }
    }

    private fun isVaild(): Boolean {
        try {
            binding.etFirstname.nameVailded()
            binding.etLastname.nameVailded()
            binding.etPassword.nameVailded()
            binding.etConform.nameVailded()
            if (binding.etPassword.text.toString() == binding.etConform.text.toString()) {
                return true
            } else {
                binding.etPassword.requestFocus()
                binding.etPassword.error = "Wroung Password"
                binding.etPassword.setText("")
                binding.etConform.setText("")
                return false
            }
        } catch (e: ValidateName) {
            binding.etFirstname.requestFocus()
            binding.etFirstname.error = e.message
            return false
        }

        return true
    }

}