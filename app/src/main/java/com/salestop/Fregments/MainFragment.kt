package com.salestop.Fregments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.salestop.*
import com.salestop.R
import com.salestop.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var dbref: DatabaseReference

    // private lateinit var db: SQLiteFriendList
    private lateinit var user: Profile
    private lateinit var userRecylerview: RecyclerView
    lateinit var sharePref: SharePref
    private lateinit var userArrayList: ArrayList<Friend>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userRecylerview = binding.userList
        sharePref = SharePref(context)
        // db = SQLiteFriendList(context,users.username.toString())
        userRecylerview.layoutManager = LinearLayoutManager(context)
        userRecylerview.setHasFixedSize(true)
        userArrayList = arrayListOf<Friend>()

        //  getFriends()

        user = sharePref.getUserDetails()
        getUserData()
        binding.txUser.text = user.username
        binding.btAdduser.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, FriendListFragment()).commit()
        }
        binding.logout.setOnClickListener {
            sharePref.logout()
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, OnboardingFragment()).commit()
        }
    }


    private fun getUserData() {
        val us = user.username.toString()
        val dbref = FirebaseDatabase.getInstance().getReference(us)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {

                        val keyvalue = userSnapshot.getValue(Friend::class.java)
                        userArrayList.add(keyvalue!!)
                    }
                    userRecylerview.adapter = MyAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}