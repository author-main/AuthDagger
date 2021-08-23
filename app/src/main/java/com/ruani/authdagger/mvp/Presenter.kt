package com.ruani.authdagger.mvp

import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.TPresenter

class Presenter<T: Contract.IView>(view: T): TPresenter<T, Model>(view) {
    init {
        attachModel(Model())
    }
}