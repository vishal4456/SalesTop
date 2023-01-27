package com.salestop.Fregments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.database.*
import com.salestop.*
import com.salestop.R
import com.salestop.databinding.FragmentFriendListBinding


class FriendListFragment : Fragment() {

    private lateinit var binding:FragmentFriendListBinding
    private lateinit var db: SQLiteFriendList
    private lateinit var database: DatabaseReference
    private lateinit var sharePref: SharePref
    private lateinit var user : Profile
    var maxid : Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =FragmentFriendListBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharePref = SharePref(context)
         user = sharePref.getUserDetails()
        onBackPressed()
        database = FirebaseDatabase.getInstance().getReference().child(user.username.toString())
        database.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    maxid = snapshot.childrenCount
                    //  Toast.makeText(context,maxid.toString(),Toast.LENGTH_LONG).show()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

       // db = SQLiteFriendList(context,"ashok123")
        val firstname = binding.etFirstname.text.toString()
        val lastname = binding.etLastname.text.toString()

        binding.btAddfriend.setOnClickListener {
            sendData()
        }
    }
    private fun sendData() {
        val list :MutableList<Friend>
       /* val friend = Friend("onkar","Bhandri")
        list.add(friend)*/
        val friend = Friend(binding.etFirstname.text.toString(),
            binding.etLastname.text.toString())

        database.child((maxid+1).toString()).setValue(friend).addOnSuccessListener{
            binding.etFirstname.text?.clear()
            binding.etLastname.text?.clear()

            Toast.makeText(context, "Added Data Successfuly", Toast.LENGTH_LONG).show()
            parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,MainFragment()).commit()
        }
            .addOnFailureListener {
                Toast.makeText(context, "Fail to add", Toast.LENGTH_LONG).show()

            }

    }

    fun openFragment(fragment:Fragment)
    {
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main,fragment).commit()
    }
    fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(),object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                openFragment(OnboardingFragment())
            }

        })
    }


}