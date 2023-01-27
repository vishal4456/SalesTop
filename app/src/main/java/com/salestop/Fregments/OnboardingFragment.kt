package com.salestop.Fregments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salestop.R
import com.salestop.SharePref
import com.salestop.databinding.FragmentOnboardingBinding


class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding
    private lateinit var sharePref: SharePref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharePref = SharePref(context)
        onClick()
        isLogin()
    }

    private fun isLogin() {
        val user = sharePref.getUserDetails()
        if (user.username==null)
        {

        }
        else
        {
            parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,MainFragment()).commit()
        }

    }

    private fun onClick() {
        binding.btSign.setOnClickListener {
           parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,SignupFragment()).commit()
        }
        binding.btLogin.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,LoginFragment()).commit()
        }
    }

}