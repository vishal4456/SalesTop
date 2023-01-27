package com.salestop.Ext

import androidx.appcompat.widget.AppCompatEditText
import com.salestop.ValidateName

fun AppCompatEditText.nameVailded():Boolean
{
when{
    this.text.toString().trim().isEmpty() ->
    throw ValidateName("Please Enter the Name")
    else -> return true
    }
}