package com.ruani.authdagger.mvp

import android.view.View
import com.ruani.authdagger.abstractData.AuthData
import com.ruani.authdagger.abstractData.Contract
import com.ruani.authdagger.abstractData.TPresenter

class Presenter<T: Contract.IView>(view: T): TPresenter<T, Model>(view) {
    init {
        attachModel(Model())
    }
}