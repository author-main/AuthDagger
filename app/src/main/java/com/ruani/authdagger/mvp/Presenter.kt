package com.ruani.authdagger.mvp

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.ruani.authdagger.MainActivity
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.TModel
import com.ruani.authdagger.abstract_data.TPresenter
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.interfaces.AuthDialog
import com.ruani.authdagger.interfaces.AuthServer
import com.ruani.authdagger.log
import com.ruani.authdagger.mvp.presenter_classes.FirebaseDialog
import com.ruani.authdagger.mvp.presenter_classes.ViewPasswordHelper

class Presenter<T: Contract.IView>: TPresenter<T, TModel<AuthServer>, AuthDialog<T>>() {
    private var viewPasswordHelper: ViewPasswordHelper? = null
    init {
        attachModel(Model())
        attachDialog(FirebaseDialog())
    }

    override fun attachView(v: T) {
        super.attachView(v)
        val symbols = getView()?.getSymbolViews()
        symbols?.let {
            viewPasswordHelper = ViewPasswordHelper(it)
            viewPasswordHelper?.onCompleted = {
                setPassword("")
                //viewPasswordHelper?.changeSymbolsColor(null)
            }
        }
    }

    override fun changePassword(symbol: String?) {
        viewPasswordHelper?.changeSymbol(symbol)
        super.changePassword(symbol)
    }

    override fun setPassword(value: String?) {
        super.setPassword(value)
        viewPasswordHelper?.changeSymbolsColor(value)
    }
}