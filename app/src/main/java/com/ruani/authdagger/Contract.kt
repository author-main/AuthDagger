package com.ruani.authdagger

interface Contract {
    interface IView {
        fun accessed()
    }

    interface IModel{
        fun putPassword(password: String)
        fun putEmail(email: String)
        fun signIn(): Boolean
    }

    interface IPresenter<T: Contract.IView>{
        fun attachView(v: T)
        fun detachView()
        fun setModel(m: Contract.IModel)
        fun signIn()
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

    override fun setModel(m: Contract.IModel) {
        this.model = m
    }

    fun getView(): T? = view

    override fun signIn() {
        model?.let{ model_ ->
            if (model_.signIn())
                view?.accessed()
        }
    }

}