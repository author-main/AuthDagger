package com.ruani.authdagger.mvp

import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.TModel
import com.ruani.authdagger.abstract_data.TPresenter
import com.ruani.authdagger.interfaces.AuthDialog
import com.ruani.authdagger.interfaces.AuthServer
import com.ruani.authdagger.interfaces.FingerPrint
import com.ruani.authdagger.mvp.presenter_classes.FirebaseDialog
import com.ruani.authdagger.mvp.presenter_classes.ViewSymbols

class Presenter<T: Contract.IView>: TPresenter<T, TModel<AuthServer, FingerPrint<Contract.IView>>, AuthDialog<T>>() {
    private var viewSymbols: ViewSymbols? = null
    init {
        attachModel(Model())
        attachDialog(FirebaseDialog())
    }

    override fun attachView(v: T) {
        super.attachView(v)
        val symbols = getView()?.getSymbolViews()
        symbols?.let {
            viewSymbols = ViewSymbols(it)
            viewSymbols?.onFinish = {
                setPassword("")
            }
        }
    }

    override fun updatePassword(value: String?) {
        viewSymbols?.setValue(value)
    }

    override fun setPassword(value: String?, show: Boolean) {
        super.setPassword(value, show)
        viewSymbols?.setValue(value, show)
    }
}