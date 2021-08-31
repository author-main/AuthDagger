package com.ruani.authdagger.abstract_data

import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableField
import com.ruani.authdagger.LENGTH_PASSWORD
import com.ruani.authdagger.interfaces.AuthDialog
import com.ruani.authdagger.interfaces.AuthServer
import com.ruani.authdagger.log
import com.ruani.authdagger.mvp.model_classes.FingerPrint

interface Contract {
    interface IView {
        fun onResultAuth(authAction: auth_data.AuthAction, authValue: auth_data.AuthValue)
        fun getSymbolViews(): Array<TextView>?
        fun enabledFingerPrint(value: Boolean?)
        //fun clickView(v: auth_data.AuthButton)
    }

    interface IModel{
        fun putPassword(password: String)
        fun getEmail(): String?
        fun putEmail(email: String)
        fun getPassword(): String?
    }

    interface IPresenter<T: IView, M: IModel, D: AuthDialog<T>, F: FingerPrint<T>>{
        fun setPassword(value: String?, show: Boolean = true)
//        fun updatePassword(value: String?)
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
            //model?.server?.executeRequest(action, email, password)
        }
        //authDialog?.setView(view)
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

/*fun showDialogProgress(){
        authDialog?.showDialogProgress()
    }*/



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
        model = m
        model?.server?.onAuthServerResult = {action, value ->
            authDialog?.hideDialogProgress()
            clearPassword()
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
                }
            }/* else
                clearPassword()*/
            view?.onResultAuth(action, value)
        }
    }

    fun getView()  = view

   // fun getModel() = model*/

    fun onClick(v: View) {
        val tag = v.tag.toString()
        when (tag) {
            "register" -> {
                //view?.clickView(auth_data.AuthButton.BUTTON_REGISTER)
                clearPassword()
                authDialog?.showDialogRegister(email.get())
            }
                //view?.clickView(auth_data.AuthButton.BUTTON_REGISTER)
            "restore" -> {
                clearPassword()
                authDialog?.showDialogRestore(email.get())
            }
                //view?.clickView(auth_data.AuthButton.BUTTON_RESTORE)
            "finger" -> {
                clearPassword()
                    //val fingerPassword = model?.getPassword()
                fingerPrint?.authenticate()
                //view?.clickView(auth_data.AuthButton.BUTTON_FINGER)
              /*  model?.getPassword()?.let {
                    setPassword(it)
                    executeAuthRequest(auth_data.AuthAction.SIGNIN)
                }*/
            }
            "delete" ->
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
        log(email.get() + ", " +  password.get())
        authDialog?.showDialogProgress()
        model?.server?.executeRequest(type, email.get(), password.get())
       // clearPassword()
    }

    private fun clearPassword(){
        setPassword("")
    }

}