package com.ruani.authdagger

const val FILE_PREFERENCES = "settings"
const val KEY_MAIL = "key_mail"
const val KEY_PASSWORD = "key_password"

fun getAppContext() = AuthApplication.applicationContext()