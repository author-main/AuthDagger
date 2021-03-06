package com.ruani.authdagger.mvp.model_classes

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.ruani.authdagger.R
import com.ruani.authdagger.mvp.Contract
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.mvp.interfaces.FingerPrint
import com.ruani.authdagger.helpers.getAppContext
import com.ruani.authdagger.helpers.getStringResource

class AuthFingerPrint<T:Contract.IView>: FingerPrint<T>(){
    override fun authenticate() {
        if (!getAvailable())
            return
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getStringResource(R.string.biometric_title) ?: "")
            .setConfirmationRequired(false)
            .setNegativeButtonText(getStringResource(R.string.button_cancel) ?: "")
            .build()
        val biometricPrompt = createBiometricPrompt()
        biometricPrompt?.authenticate(promptInfo)
    }

    private fun createBiometricPrompt(): BiometricPrompt?{
        if (getContext() == null)
            return null
        val executor = ContextCompat.getMainExecutor(getAppContext())
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onAuthBiometricComplete?.invoke(auth_data.FingerValue.FINGER_ERROR)
            }
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAuthBiometricComplete?.invoke(auth_data.FingerValue.FINGER_COMPLETE)
            }
        }
        return BiometricPrompt(getContext()!!, executor, callback)
    }
}