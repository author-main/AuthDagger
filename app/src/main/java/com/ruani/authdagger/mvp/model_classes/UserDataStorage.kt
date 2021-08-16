package com.ruani.authdagger.mvp.model_classes

import android.content.Context
import android.content.SharedPreferences
import com.ruani.authdagger.FILE_PREFERENCES
import com.ruani.authdagger.KEY_MAIL
import com.ruani.authdagger.KEY_PASSWORD
import com.ruani.authdagger.getAppContext
import com.ruani.authdagger.interfaces.IOUserDataStorage

class UserDataStorage: IOUserDataStorage {
    private val cipherData: CipherPassword  = CipherPassword()
    private val preferences: SharedPreferences = getAppContext().getSharedPreferences(
        FILE_PREFERENCES, Context.MODE_PRIVATE)

    override fun putPassword(password: String) {
        preferences.edit().putString(KEY_PASSWORD, password).apply()
    }

    override fun getPassword() = preferences.getString(KEY_PASSWORD, null)

    override fun getEmail() = preferences.getString(KEY_MAIL, null)

    override fun putEmail(email: String) {
        preferences.edit().putString(KEY_MAIL, email).apply()
    }
}