package com.ruani.authdagger

import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import androidx.core.content.ContextCompat

const val FILE_PREFERENCES = "settings"
const val KEY_MAIL = "key_mail"
const val KEY_PASSWORD = "key_password"
const val DEFAULT_COLOR = Color.LTGRAY

fun getAppContext() = AuthApplication.applicationContext()
fun log(message: String?){
    if (message.isNullOrBlank())
        return
    Log.v("authdagger", message)
}

fun getColorFromTheme(id: Int): Int {
    val typedValue = TypedValue()
    return if (getAppContext().theme.resolveAttribute(id, typedValue, true))
        typedValue.data
    else
        DEFAULT_COLOR
}
