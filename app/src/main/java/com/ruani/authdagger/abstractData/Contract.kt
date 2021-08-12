package com.ruani.authdagger.abstractData

import android.view.View

interface MpvView {
    fun onSignin()
}

interface MvpPresenter<T: MpvView, M: MvpModel>{
    fun attachView(v: T)
    fun detachView()
    fun attachModel(m: M)
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

    interface IPresenter<T: Contract.IView, M: IModel>: MvpPresenter<T, M>{
        fun signin()
        fun restore()
        fun register()
        fun error(error: AuthData.AuthValue)
    }
}