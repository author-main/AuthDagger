package com.ruani.authdagger

import android.view.View

interface Contract {
    interface IView {
        fun accessed()
        fun showError(error: AuthData.AuthValue)
    }

    interface IModel{
        fun putPassword(password: String)
        fun getEmail(): String
        fun putEmail(email: String)
        fun viewOnClick(v: View): AuthData.AuthValue
        fun signIn(): Boolean
    }

    interface IPresenter<T: Contract.IView>{
        fun attachView(v: T)
        fun detachView()
        fun attachModel(m: Contract.IModel)
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

    override fun attachModel(m: Contract.IModel) {
        model = m
    }

    fun getView()  = view

    fun getModel() = model

    fun onClick(v: View) {
        model?.let {model_ ->
            when (val result = model_.viewOnClick(v)) {
                AuthData.AuthValue.COMPLETE_SIGN ->
                    view?.accessed()
                AuthData.AuthValue.COMPLETE_REGISTER -> {

                }
                AuthData.AuthValue.COMPLETE_RESTORE -> {

                }
                else ->
                    view?.showError(result)
            }
        }
    }

}