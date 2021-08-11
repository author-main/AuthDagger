package com.ruani.authdagger.abstractData

import android.view.View

interface MpvView {
    fun onSignin()
}

interface MvpPresenter<T: MpvView>{
    fun attachView(v: T)
    fun detachView()
}

interface MvpModel{
    fun putPassword(password: String)
    fun getEmail(): String
    fun putEmail(email: String)
}

interface Contract {
    interface IView: MpvView {
        fun onRegistered()
        fun onRestored()
        fun onError(error: AuthData.AuthValue)
    }

    interface IModel: MvpModel{
        fun viewOnClick(v: View): AuthData.AuthValue
    }

    interface IPresenter<T: Contract.IView>: MvpPresenter<T>{
        fun signin()
        fun restore()
        fun register()
        fun error(error: AuthData.AuthValue)
    }
}

abstract class Presenter<T: Contract.IView>: Contract.IPresenter<T>{
    private var view: T? = null
    private var model: Contract.IModel? = null

    override fun attachView(v: T) {
        view = v
    }

    override fun detachView() {
        view = null
    }

    fun getView()  = view

    fun getModel() = model

    fun onClick(v: View) {
        model?.let {model_ ->
            when (val result = model_.viewOnClick(v)) {
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