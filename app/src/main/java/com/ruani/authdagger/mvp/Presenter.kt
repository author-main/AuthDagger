package com.ruani.authdagger.mvp

import com.ruani.authdagger.AuthApplication
import com.ruani.authdagger.mvp.interfaces.*
import com.ruani.authdagger.mvp.presenter_classes.FirebaseDialog
import com.ruani.authdagger.mvp.presenter_classes.Dyer
import javax.inject.Inject

class Presenter: TPresenter<Contract.IView, TModel<AuthServer, FingerPrint<Contract.IView>, IOUserDataStorage>, AuthDialog<Contract.IView>>() {
    private var dyer: Dyer? = null
    @Inject
    lateinit var model: Model
    @Inject
    lateinit var dialogs: FirebaseDialog<Contract.IView>

    init {
        AuthApplication.getComponent().injectsPresenter(this)
        attachModel(model)
        attachDialog(dialogs)
    }

    override fun attachView(v: Contract.IView) {
        super.attachView(v)
        val symbols = getView()?.getSymbolViews()
        symbols?.let {
            dyer = Dyer(it)
            dyer?.setValue(getSymbolsValue(), false)
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