package com.ruani.authdagger.abstract_data

import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableField
import com.ruani.authdagger.LENGTH_PASSWORD
import com.ruani.authdagger.interfaces.AuthServer
import com.ruani.authdagger.log

interface MpvView {
    fun onResultAuth(authAction: auth_data.AuthAction, authValue: auth_data.AuthValue)
}

interface MvpPresenter<T: MpvView, M: MvpModel>{
    fun attachView(v: T)
    fun detachView()
}

interface MvpModel{
    fun putPassword(password: String)
    fun getEmail(): String?
    fun putEmail(email: String)
}

interface Contract {
    interface IView: MpvView {
        fun getSymbolViews(): Array<TextView>?
        fun clickView(v: auth_data.AuthButton)
    }

    interface IModel: MvpModel{
        fun checkAuth(type: auth_data.AuthAction, email: String?, password: String?): auth_data.AuthValue
    }

    interface IPresenter<T: IView, M: IModel>: MvpPresenter<T, M>{
        fun setPassword(value: String)
        fun changePassword(symbol: String?)
    }
}

abstract class TModel<T: AuthServer>: Contract.IModel{
    private var authServer: T? = null

}

abstract class TPresenter<T: Contract.IView, M: Contract.IModel>: Contract.IPresenter<T, M>{
    private var view:       T?  = null
    private var model:      M?  = null
    val email:      ObservableField<String> = ObservableField()
    val password:   ObservableField<String> = ObservableField()

    init{
        password.set("")
        email.set("")
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
    }

    fun getView()  = view

   // fun getModel() = model*/

    fun onClick(v: View) {
        when (val tag = v.tag.toString()) {
            "register" ->
                view?.clickView(auth_data.AuthButton.BUTTON_REGISTER)
            "restore" ->
                view?.clickView(auth_data.AuthButton.BUTTON_RESTORE)
            "finger" ->
                view?.clickView(auth_data.AuthButton.BUTTON_FINGER)
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
          executeAuth(auth_data.AuthAction.SIGNIN)
    }

    private fun executeAuth(type: auth_data.AuthAction){
        model?.let { model_ ->
            val result = model_.checkAuth(type, email.get(), password.get())
            view?.onResultAuth(type, result)
        }
    }

}