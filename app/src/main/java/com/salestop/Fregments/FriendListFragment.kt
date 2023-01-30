package com.salestop.Fregments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import com.salestop.*
import com.salestop.Model.LoginVM
import com.salestop.R
import com.salestop.base.BaseFragment
import com.salestop.database.SharePref
import com.salestop.databinding.FragmentFriendListBinding


class FriendListFragment :
    BaseFragment<FragmentFriendListBinding>(FragmentFriendListBinding::inflate) {

    private lateinit var sharePref: SharePref
    lateinit var vm: LoginVM
    private lateinit var user: Profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm = ViewModelProvider(this)[LoginVM::class.java]
        sharePref = SharePref(context)
        user = sharePref.getUserDetails()
        onBackPressed()
        setObserver()

        binding.btAddfriend.setOnClickListener {
            val friend = Friend(binding.etFirstname.text.toString(),
                binding.etLastname.text.toString())
            vm.sendData(friend, user.username.toString())


        }
    }

    fun setObserver() {
        vm.msg.observe(viewLifecycleOwner) {
            Toast.makeText(context?.applicationContext, it, Toast.LENGTH_LONG).show()
            openFragment(MainFragment())
        }
    }


    override fun initView() {
        super.initView()
        onBackPressed()
    }

}