package com.ruani.authdagger

import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import androidx.core.content.ContextCompat
import android.R
import android.content.Context

import android.content.res.TypedArray
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


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

    private fun connectedInternet(): Boolean{
        val connectivityManager = getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }


