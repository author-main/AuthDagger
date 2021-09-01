package com.ruani.authdagger.mvp.classes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.ruani.authdagger.AuthApplication


const val FILE_PREFERENCES = "settings"
const val KEY_MAIL = "key_mail"
const val KEY_PASSWORD = "key_password"
const val LENGTH_PASSWORD = 5

const val BUTTON_DELETE = "delete"
const val BUTTON_FINGER = "finger"
const val BUTTON_REGISTER = "register"
const val BUTTON_RESTORE = "restore"

fun getAppContext() = AuthApplication.applicationContext()

fun getColorResource(id: Int) =
    ContextCompat.getColor(getAppContext(), id)

fun getStringResource(id: Int) =
    try {
        getAppContext().getString(id)
    } catch (e: Exception) {
        null
    }

fun connectedInternet(): Boolean {
    val connectivityManager =
        getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

fun validateMail(email: String): Boolean {
    return !(email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
}

/*
    fun log() можно удалить (только для отладки)
*/
fun log(message: String?) {
    if (message.isNullOrBlank())
        return
    Log.v("authdagger", message)
}