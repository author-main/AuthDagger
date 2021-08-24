package com.ruani.authdagger.abstract_data

import android.view.View
import androidx.databinding.ObservableField
import com.ruani.authdagger.LENGTH_PASSWORD
import com.ruani.authdagger.log

interface MpvView {
    fun onSignin()
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
        fun onRegistered()
        fun onRestored()
        fun onError(error: auth_data.AuthValue)
        fun clickView(v: View)
    }

    interface IModel: MvpModel{
        fun viewOnClick(v: View, email: String?, password: String?): auth_data.AuthValue
    }

    interface IPresenter<T: IView, M: IModel>: MvpPresenter<T, M>{
        fun signin()
        fun restore()
        fun register()
        fun error(error: auth_data.AuthValue)
        fun setPassword(value: String)
        fun changePassword(symbol: String?)
    }
}

abstract class TPresenter<T: Contract.IView, M: Contract.IModel>(v: T): Contract.IPresenter<T, M>{
    private var view:       T?  = v
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
       // log(email.get())
        view?.clickView(v)
     /*   model?.let {model_ ->
            when (val result = model_.viewOnClick(v, email.get(), password.get())) {
                auth_data.AuthValue.COMPLETE_SIGN ->
                    signin()

                auth_data.AuthValue.COMPLETE_REGISTER ->
                    register()

                auth_data.AuthValue.COMPLETE_RESTORE ->
                    restore()

                else ->
                    error(result)
            }
        }*/
    }

    override fun signin() {
        view?.onSignin()
    }

    override fun restore() {
        view?.onRestored()
    }

    override fun register() {
        view?.onRegistered()
    }

    override fun error(error: auth_data.AuthValue) {
        view?.onError(error)
    }

    override fun changePassword(symbol: String?) {
        var mPassword = password.get()
        if (symbol.isNullOrBlank()) {
            if (!mPassword.isNullOrBlank())
                mPassword = mPassword.dropLast(1)
        }
        else {
            val lengthPassword = mPassword?.length ?: 0 // <- удалить строку
            if (lengthPassword < LENGTH_PASSWORD)       // <- удалить строку
                mPassword += symbol
        }
        password.set(mPassword)
    }
}