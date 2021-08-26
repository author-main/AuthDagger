package com.ruani.authdagger.abstract_data

import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableField
import com.ruani.authdagger.LENGTH_PASSWORD
import com.ruani.authdagger.interfaces.AuthDialog
import com.ruani.authdagger.interfaces.AuthServer
import com.ruani.authdagger.isNotNull

interface MpvView {
    fun onResultAuth(authAction: auth_data.AuthAction, authValue: auth_data.AuthValue)
}

interface MvpPresenter<T: MpvView, M: MvpModel, D: AuthDialog>{
    fun attachView(v: T)
    fun detachView()
    fun attachDialog(dialog: D)
}

interface MvpModel {
    fun putPassword(password: String)
    fun getEmail(): String?
    fun putEmail(email: String)
    fun getPassword(): String?

}

interface Contract {
    interface IView: MpvView {
        fun getSymbolViews(): Array<TextView>?
        fun clickView(v: auth_data.AuthButton)
    }

    interface IModel: MvpModel{
        //fun checkAuth(type: auth_data.AuthAction, email: String?, password: String?): auth_data.AuthValue
    }

    interface IPresenter<T: IView, M: IModel>: MvpPresenter<T, M, AuthDialog>{
        fun setPassword(value: String)
        fun changePassword(symbol: String?)
    }


}

abstract class TModel<S: AuthServer>: Contract.IModel {
    var server: S? = null
    fun attachServer(value: S){
        server = value
    }
}


abstract class TPresenter<T: Contract.IView, M: TModel<AuthServer>>: Contract.IPresenter<T, M>{
    private var view:       T?  = null
    private var model:      M?  = null
    private var authDialog  :   AuthDialog? = null

    val email:      ObservableField<String> = ObservableField()
    val password:   ObservableField<String> = ObservableField()


    init{
        password.set("")
        email.set("")
    }


    override fun attachDialog(dialog: AuthDialog) {
        authDialog = dialog
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
            if (value != auth_data.AuthValue.COMPLETE)
                setPassword("")
            else
                password.get()?.let{
                    setPassword(it)
                }
            view?.onResultAuth(action, value)
        }
    }

    fun getView()  = view

   // fun getModel() = model*/

    fun onClick(v: View) {
        when (val tag = v.tag.toString()) {
            "register" ->
                authDialog?.showDialogRegister(email.get())
                //view?.clickView(auth_data.AuthButton.BUTTON_REGISTER)
            "restore" ->
                authDialog?.showDialogRestore(email.get())
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