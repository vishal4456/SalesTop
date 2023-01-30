package com.salestop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.salestop.Fregments.OnboardingFragment
import com.salestop.R
import com.salestop.database.SharePref

abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (layoutInflater:LayoutInflater)-> T):
    Fragment() {


     lateinit var binding : T
    private lateinit var sharePref: SharePref

 //   protected val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater)
        sharePref = SharePref(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onClick()
    }
    open fun initView(){}
    open fun onClick(){}

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
}