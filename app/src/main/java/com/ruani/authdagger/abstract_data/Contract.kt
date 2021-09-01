package com.ruani.authdagger.abstract_data

import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableField
import com.ruani.authdagger.*
import com.ruani.authdagger.interfaces.AuthDialog
import com.ruani.authdagger.interfaces.AuthServer
import com.ruani.authdagger.mvp.model_classes.FingerPrint

interface Contract {
    interface IView {
        fun onResultAuth(authAction: auth_data.AuthAction, authValue: auth_data.AuthValue)
        fun getSymbolViews(): Array<TextView>?
        fun enabledFingerPrint(value: Boolean?)
        fun onAccessed()
    }

    interface IModel{
        fun putPassword(password: String)
        fun getEmail(): String?
        fun putEmail(email: String)
        fun getPassword(): String?
    }

    interface IPresenter<T: IView, M: IModel, D: AuthDialog<T>, F: FingerPrint<T>>{
        fun setPassword(value: String?, show: Boolean = true)
        fun attachView(v: T)
        fun detachView()
        fun attachDialog(dialog: D)
        fun attachFingerPrint(value: F)
    }
}

abstract class TModel<S: AuthServer>: Contract.IModel {
    var server: S? = null
    fun attachServer(value: S){
        server = value
    }
}

abstract class TPresenter<T: Contract.IView, M: TModel<AuthServer>, D: AuthDialog<T>, F: FingerPrint<T> >: Contract.IPresenter<T, M, D, F>{

    private var view        :      T?  = null
    private var model       :      M?  = null
    private var authDialog  :      D? = null
    private var fingerPrint :      F? = null

    val email:      ObservableField<String> = ObservableField()
    val password:   ObservableField<String> = ObservableField()

    init{
        clearPassword()
        email.set("")
    }

    override fun attachDialog(dialog: D) {
        authDialog = dialog
        authDialog?.onDialogResult= {action, email, password ->
            this.email.set(email)
            setPassword(password)
            executeAuthRequest(action)
        }
    }

    override fun attachFingerPrint(value: F) {
        fingerPrint = value
        fingerPrint?.onAuthBiometricComplete = {fingerValue ->
            if (fingerValue == auth_data.FingerValue.FINGER_COMPLETE) {
                val fingerPassword = model?.getPassword()
                fingerPassword?.let{
                    setPassword(it, false)
                    executeAuthRequest(auth_data.AuthAction.SIGNIN)
                }

            }
        }
    }

    override fun setPassword(value: String?, show: Boolean) {
        password.set(value)
    }

    override fun attachView(v: T) {
        view = v
        authDialog?.setView(view)
        fingerPrint?.setView(view)
        view?.enabledFingerPrint(fingerPrint?.getAvailable())
        email.set(model?.getEmail())
    }

    override fun detachView() {
        view = null
    }

    fun attachModel(m: M) {
        var signIn = false
        model = m
        model?.server?.onAuthServerResult = {action, value ->
            authDialog?.hideDialogProgress()
            if (value == auth_data.AuthValue.COMPLETE) {
                val serverMail = model?.server?.getServerEmail()
                val serverPassword = model?.server?.getServerPassword()
                if (action == auth_data.AuthAction.RESTORE) {
                    serverMail?.let {
                        model?.putEmail(it)
                        email.set(it)
                    }
                }
                if (action == auth_data.AuthAction.REGISTER) {
                    serverMail?.let {
                        model?.putEmail(it)
                        email.set(it)
                    }
                    serverPassword?.let {
                        model?.putPassword(it)
                    }
                }

                if (action == auth_data.AuthAction.SIGNIN) {
                    serverMail?.let {
                        model?.putEmail(it)
                    }
                    serverPassword?.let {
                        model?.putPassword(it)
                    }
                    signIn = true
                }
            }
            if (signIn)
                view?.onAccessed()
            else
                view?.onResultAuth(action, value)
        }
    }

    fun getView()  = view

    fun onClick(v: View) {
        when (val tag = v.tag.toString()) {
            BUTTON_REGISTER -> {
                clearPassword()
                authDialog?.showDialogRegister(email.get())
            }
            BUTTON_RESTORE -> {
                clearPassword()
                authDialog?.showDialogRestore(email.get())
            }
            BUTTON_FINGER -> {
                clearPassword()
                fingerPrint?.authenticate()
            }
            BUTTON_DELETE ->
                changePassword(null)
            else ->
                changePassword(tag)
        }
    }

    private fun changePassword(symbol: String?) {
        var signIn = false
        var mPassword = password.get()
        if (symbol.isNullOrBlank()) {
            if (!mPassword.isNullOrBlank())
                mPassword = mPassword.dropLast(1)
        }
        else {
            val lengthPassword = mPassword?.length ?: 0 // <- удалить строку
            if (lengthPassword < LENGTH_PASSWORD) {      // <- удалить строку
                mPassword += symbol
                signIn = mPassword?.length == LENGTH_PASSWORD
            }
        }
        password.set(mPassword)
        updatePassword(mPassword)
        if (signIn)
            executeAuthRequest(auth_data.AuthAction.SIGNIN)
    }

    abstract fun updatePassword(value: String?)

    private fun executeAuthRequest(type: auth_data.AuthAction){
        authDialog?.showDialogProgress()
        model?.server?.executeRequest(type, email.get(), password.get())
    }

    private fun clearPassword(){
        setPassword("")
    }

}