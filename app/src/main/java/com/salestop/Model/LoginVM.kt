package com.salestop.Model

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.salestop.Fregments.MainFragment
import com.salestop.Friend
import com.salestop.Profile
import com.salestop.R
import com.salestop.database.SharePref

class LoginVM : ViewModel() {
    var _msg = MutableLiveData<String>()
    val msg: LiveData<String>
        get() = _msg


    fun signup(profile: Profile) {
        val database = FirebaseDatabase.getInstance().getReference("user")

        database.child(profile.username.toString())
            .setValue(profile)
            .addOnSuccessListener {
                _msg.postValue("Data Added Scuccessful")

            }
            .addOnFailureListener {
                _msg.postValue("Data Added Failed")
            }
    }


     fun sendData(friend: Friend,user:String) {
        var maxid : Long  = 0
       val database = FirebaseDatabase.getInstance().getReference().child(user)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.childrenCount
                    //  Toast.makeText(context,maxid.toString(),Toast.LENGTH_LONG).show()
                    addFriend(friend,maxid,user)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })}
    fun addFriend(friend: Friend, maxid: Long,user:String)
    {
        val database = FirebaseDatabase.getInstance().getReference().child(user)
        database.child((maxid + 1).toString())
            .setValue(friend)
            .addOnSuccessListener {


            _msg.postValue("Added Data Successfuly")

        }
            .addOnFailureListener {
                _msg.postValue("Failed to Added")

            }

    }



}