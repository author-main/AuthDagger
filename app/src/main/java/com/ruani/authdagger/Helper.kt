package com.ruani.authdagger

import android.util.Log

const val FILE_PREFERENCES = "settings"
const val KEY_MAIL = "key_mail"
const val KEY_PASSWORD = "key_password"

fun getAppContext() = AuthApplication.applicationContext()
fun log(message: String?){
    if (message.isNullOrBlank())
        return
    Log.v("authdagger", message)
}