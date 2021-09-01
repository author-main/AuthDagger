package com.ruani.authdagger.mvp

import com.ruani.authdagger.mvp.interfaces.*
import com.ruani.authdagger.mvp.presenter_classes.FirebaseDialog
import com.ruani.authdagger.mvp.presenter_classes.Dyer

class Presenter<T: Contract.IView>: TPresenter<T, TModel<AuthServer, FingerPrint<Contract.IView>, IOUserDataStorage>, AuthDialog<T>>() {
    private var dyer: Dyer? = null
    init {
        attachModel(Model())
        attachDialog(FirebaseDialog())
    }

    override fun attachView(v: T) {
        super.attachView(v)
        val symbols = getView()?.getSymbolViews()
        symbols?.let {
            dyer = Dyer(it)
            dyer?.onFinish = {
                setPassword("")
            }
        }
    }

    override fun updatePassword(value: String?) {
        dyer?.setValue(value)
    }

    override fun setPassword(value: String?, show: Boolean) {
        super.setPassword(value, show)
        dyer?.setValue(value, show)
    }
}