package com.ruani.authdagger.abstract_data

import android.view.View
import androidx.databinding.ObservableField

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
        fun onError(error: AuthData.AuthValue)
    }

    interface IModel: MvpModel{
        fun viewOnClick(v: View, email: String?, password: String?): AuthData.AuthValue
    }

    interface IPresenter<T: Contract.IView, M: IModel>: MvpPresenter<T, M>{
        fun signin()
        fun restore()
        fun register()
        fun error(error: AuthData.AuthValue)
    }
}

abstract class TPresenter<T: Contract.IView, M: Contract.IModel>(v: T): Contract.IPresenter<T, M>{
    private var view:       T?  = v
    private var model:      M?  = null
    private val email:      ObservableField<String> = ObservableField()
    private val password:   ObservableField<String> = ObservableField()

    override fun attachView(v: T) {
        view = v
    }

    override fun detachView() {
        view = null
    }

    fun attachModel(m: M) {
        model = m
    }

    /*fun getView()  = view

    fun getModel() = model*/

    fun onClick(v: View) {
        model?.let {model_ ->
            when (val result = model_.viewOnClick(v, email.get(), password.get())) {
                AuthData.AuthValue.COMPLETE_SIGN ->
                    signin()

                AuthData.AuthValue.COMPLETE_REGISTER ->
                    register()

                AuthData.AuthValue.COMPLETE_RESTORE ->
                    restore()

                else ->
                    error(result)
            }
        }
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

    override fun error(error: AuthData.AuthValue) {
        view?.onError(error)
    }
}