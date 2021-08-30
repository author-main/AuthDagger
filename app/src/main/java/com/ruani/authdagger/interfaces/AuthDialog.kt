package com.ruani.authdagger.interfaces

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.auth_data

abstract class AuthDialog<T:Contract.IView> {
    private var activity: AppCompatActivity? = null
    var onDialogResult:((action: auth_data.AuthAction, email: String, password: String) -> Unit)? = null
    @JvmName("setContext1")
    fun setView(v: T?){
        if (v != null)
            activity = v as AppCompatActivity
    }
    fun getContext() = activity
    abstract fun showDialogRegister(email: String?)
    abstract fun showDialogRestore (email: String?)
}