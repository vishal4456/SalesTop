package com.salestop.database

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.salestop.utils.Constants
import com.salestop.Profile
import com.salestop.utils.Util


class SharePref(context: Context?) {
    private var sharedPreferences: SharedPreferences
    private val TAG = "USER_DETAILS"

    init {
        sharedPreferences =
            context?.getSharedPreferences(Util.sharedPrefFile, Context.MODE_PRIVATE)!!

    }

    fun storeUserDetails(data: Profile) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(data)
        editor.putString(Constants.userDetails, json)
        editor.apply()


    }

    fun getUserDetails(): Profile {
        val gson = Gson()
        val json: String = sharedPreferences.getString(Constants.userDetails, "").toString()
        Log.d(TAG, json)
        if (json.isBlank()) {
            return Profile()
        }
        return gson.fromJson(json, Profile::class.java)
    }

    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


}