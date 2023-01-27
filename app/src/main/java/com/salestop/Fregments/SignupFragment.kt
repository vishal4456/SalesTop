package com.salestop.Fregments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.salestop.*
import com.salestop.Ext.nameVailded
import com.salestop.databinding.FragmentSignupBinding
import java.util.*


class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onClick()
        onBackPressed()

    }

    private fun sendData() {
        val list: MutableList<Friend>
        database = FirebaseDatabase.getInstance().getReference("user")
        //   val friend = Friend("onkar","Bhandri")
        //list.add(friend)
        val profile = Profile(
            binding.etFirstname.text.toString(),
            binding.etLastname.text.toString(),
            binding.etUsername.text.toString(),
            binding.etDob.text.toString(),
            getGender(),
            binding.etPassword.text.toString(),
        )

        database.child(binding.etUsername.text.toString()).setValue(profile).addOnSuccessListener {
            binding.etFirstname.text?.clear()
            binding.etLastname.text?.clear()
            binding.etUsername.text?.clear()

            Toast.makeText(context, "Added Data Successfuly", Toast.LENGTH_LONG).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, OnboardingFragment()).commit()

        }
            .addOnFailureListener {
                Toast.makeText(context, "Fail to add", Toast.LENGTH_LONG).show()

            }

    }

    fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment).commit()
    }

    fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                openFragment(OnboardingFragment())
            }

        })
    }

    private fun getGender(): String {
        val checked = binding.rgGender.checkedRadioButtonId
        val gender = view?.findViewById<RadioButton>(checked)
        return gender?.text.toString()

    }

    private fun onClick() {
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
                sendData()
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