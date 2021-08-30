package com.ruani.authdagger.interfaces

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.auth_data

abstract class AuthDialog<T:Contract.IView> {
    private var activity: Context? = null
    var onDialogResult:((action: auth_data.AuthAction, email: String, password: String?) -> Unit)? = null
    //var onGetContext:(() -> AppCompatActivity)?
    @JvmName("setContext1")
    fun setView(v: T?){
        if (v != null)
            activity = v as AppCompatActivity
    }

    abstract fun showDialogRegister(email: String?)
    abstract fun showDialogRestore (email: String?)
    abstract fun showDialogProgress()
    abstract fun hideDialogProgress()
    fun getContext() = activity
}