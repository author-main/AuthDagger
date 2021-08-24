package com.ruani.authdagger

import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import androidx.core.content.ContextCompat
import android.R

import android.content.res.TypedArray




const val FILE_PREFERENCES = "settings"
const val KEY_MAIL = "key_mail"
const val KEY_PASSWORD = "key_password"
const val AUTHFINGER_COMPLETE = "authfinger_complete"
const val LENGTH_PASSWORD = 5

fun getAppContext() = AuthApplication.applicationContext()
fun log(message: String?){
    if (message.isNullOrBlank()) {
        Log.v("authdagger", "empty")
        return
    }
    Log.v("authdagger", message)
}

fun getColorResource(id: Int) =
    ContextCompat.getColor(getAppContext(), id)
    /*val typedValue = TypedValue()
    return if (getAppContext().theme.resolveAttribute(id, typedValue, true)) {
        val color = typedValue.data.toString(16)
        Integer.parseInt(color, 16)
        //typedValue.data
    }
    else
        DEFAULT_COLOR*/

