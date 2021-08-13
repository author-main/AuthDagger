package com.ruani.authdagger.mvp

import android.view.View
import com.ruani.authdagger.abstractData.AuthData
import com.ruani.authdagger.abstractData.Contract

class Presenter<T: Contract.IView, M: Contract.IModel>(v: T, m: M): Contract.IPresenter<T, M>{
    private var view:  T?  = v
    private var model: M   = m

    override fun attachView(v: T) {
        view = v
    }

    override fun detachView() {
        view = null
    }

    fun getView()  = view

    fun getModel() = model

    fun onClick(v: View) {
        when (val result = model.viewOnClick(v)) {
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

    /*override fun attachModel(m: M) {
        model = m
    }*/
}