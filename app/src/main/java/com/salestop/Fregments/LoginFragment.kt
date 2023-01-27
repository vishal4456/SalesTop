package com.salestop.Fregments

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.database.*
import com.salestop.Profile
import com.salestop.R
import com.salestop.SharePref
import com.salestop.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    // private lateinit var db: SQLiteHelper
    lateinit var list: MutableList<Profile>
    lateinit var sharePref: SharePref
    private lateinit var dbref: DatabaseReference
    private lateinit var userArrayList: ArrayList<Profile>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // db = SQLiteHelper(context)
        sharePref = SharePref(context)


        binding.txSignup.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, SignupFragment()).commit()
        }
        onBackPressed()
        binding.btLogin.setOnClickListener {
            getUserData()


            // break
        }

    }


    fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment).commit()
    }

    fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    openFragment(OnboardingFragment())
                }

            })
    }

    @SuppressLint("Range")
    private fun login(cursor: Cursor) {
        val profile = Profile(cursor.getString(cursor.getColumnIndex("firstname")),
            cursor.getString(cursor.getColumnIndex("lastname")),
            cursor.getString(cursor.getColumnIndex("user")),
            cursor.getString(cursor.getColumnIndex("bod")),
            cursor.getString(cursor.getColumnIndex("gender")),
            cursor.getString(cursor.getColumnIndex("password")))
        if (binding.etPassword.text.toString() == profile.password) {
            //  Toast.makeText(context, "", Toast.LENGTH_LONG).show()
            sharePref.storeUserDetails(profile)
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, MainFragment()).commit()
        }

    }

    private fun getUserData() {


        dbref = FirebaseDatabase.getInstance().getReference("user")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val keyvalue = userSnapshot.getValue(Profile::class.java)
                        //  Toast.makeText(context,keyvalue?.username.toString(),Toast.LENGTH_LONG).show()
                        // Toast.makeText(context,keyvalue?.password.toString(),Toast.LENGTH_LONG).show()

                        if (keyvalue?.username.toString() == binding.etUsername.text.toString()) {
                            if (keyvalue?.password.toString() == binding.etPassword.text.toString()) {
                                sharePref.storeUserDetails(keyvalue!!)
                                Toast.makeText(context, "Logic Successful..", Toast.LENGTH_SHORT)
                                    .show()

                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.nav_host_fragment_activity_main, MainFragment())
                                    .commit()
                            } else {
                                Toast.makeText(context, "Logic Failed..", Toast.LENGTH_SHORT).show()

                            }
                        }
                        //Toast.makeText(context, keyvalue?.username.toString(), Toast.LENGTH_LONG).show()
                        //  userArrayList.add(keyvalue!!)
                    }
                    /* userArrayList.forEach {
                         Toast.makeText(context, it.username, Toast.LENGTH_LONG).show()
                     }*/
                    // Toast.makeText(context,userArrayList[1].firstName,Toast.LENGTH_LONG).show()
                }
            }



            override fun onCancelled(error: DatabaseError) {
            }
        })


    }
}