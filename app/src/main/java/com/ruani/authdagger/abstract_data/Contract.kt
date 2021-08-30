package com.ruani.authdagger.abstract_data

import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableField
import com.ruani.authdagger.LENGTH_PASSWORD
import com.ruani.authdagger.interfaces.AuthDialog
import com.ruani.authdagger.interfaces.AuthServer
import com.ruani.authdagger.isNotNull

interface Contract {
    interface IView {
        fun onResultAuth(authAction: auth_data.AuthAction, authValue: auth_data.AuthValue)
        fun getSymbolViews(): Array<TextView>?
        fun clickView(v: auth_data.AuthButton)
    }

    interface IModel{
        fun putPassword(password: String)
        fun getEmail(): String?
        fun putEmail(email: String)
        fun getPassword(): String?
    }

    interface IPresenter<T: IView, M: IModel, D: AuthDialog>{
        fun setPassword(value: String)
        fun changePassword(symbol: String?)
        fun attachView(v: T)
        fun detachView()
        fun attachDialog(dialog: D)
    }
}

abstract class TModel<S: AuthServer>: Contract.IModel {
    var server: S? = null
    fun attachServer(value: S){
        server = value
    }
}

abstract class TPresenter<T: Contract.IView, M: TModel<AuthServer>, D: AuthDialog >: Contract.IPresenter<T, M, D>{
    private var view        :       T?  = null
    private var model       :      M?  = null
    private var authDialog  :   D? = null

    val email:      ObservableField<String> = ObservableField()
    val password:   ObservableField<String> = ObservableField()

    init{
        password.set("")
        email.set("")
    }

    override fun attachDialog(dialog: D) {
        authDialog = dialog
        authDialog?.onDialogResult= {action, email, password ->
            model?.server?.executeRequest(action, email, password)
        }
    }

    override fun setPassword(value: String) {
        password.set(value)
    }

    override fun attachView(v: T) {
        view = v
    }

    override fun detachView() {
        view = null
    }

    fun attachModel(m: M) {
        model = m
        model?.server?.onAuthServerResult = {action, value ->
            setPassword("")
            if (value != auth_data.AuthValue.COMPLETE) {
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
            }
            view?.onResultAuth(action, value)
        }
    }

    fun getView()  = view

   // fun getModel() = model*/

    fun onClick(v: View) {
        when (val tag = v.tag.toString()) {
            "register" -> {
                setPassword("")
                authDialog?.showDialogRegister(email.get())
            }
                //view?.clickView(auth_data.AuthButton.BUTTON_REGISTER)
            "restore" -> {
                setPassword("")
                authDialog?.showDialogRestore(email.get())
            }
                //view?.clickView(auth_data.AuthButton.BUTTON_RESTORE)
            "finger" -> {
                model?.getPassword()?.let {
                    setPassword(it)
                    executeAuthRequest(auth_data.AuthAction.SIGNIN)
                }
            }
            "delete" ->
                changePassword(null)
            else ->
                changePassword(tag)
        }
    }

    override fun changePassword(symbol: String?) {
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
        if (signIn)
          executeAuthRequest(auth_data.AuthAction.SIGNIN)
    }

    private fun executeAuthRequest(type: auth_data.AuthAction){
        model?.server?.executeRequest(type, email.get(), password.get())
    }

}