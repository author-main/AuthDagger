package com.ruani.authdagger.interfaces

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.mvp.helpers.getAppContext

abstract class FingerPrint<T: Contract.IView> {
    var onAuthBiometricComplete: ((value: auth_data.FingerValue?) -> Unit)? = null
    private val available = canAuthenticate()
    private var context: AppCompatActivity? = null

    fun setView(v: T?){
        if (v != null)
            context = v as AppCompatActivity
    }

    @JvmName("getAvailable1")
    fun getAvailable() = available

    fun getContext() = context

    private fun canAuthenticate()=
        BiometricManager.from(getAppContext())
            .canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS

    abstract fun authenticate()

}