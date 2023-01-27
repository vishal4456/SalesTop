package com.salestop.utils

import android.content.Context
import android.widget.Toast

object Util {
    const val sharedPrefFile = "user"
    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }
}