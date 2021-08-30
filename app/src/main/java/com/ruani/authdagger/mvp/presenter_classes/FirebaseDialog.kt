package com.ruani.authdagger.mvp.presenter_classes

import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.interfaces.AuthDialog

class FirebaseDialog<T: Contract.IView>: AuthDialog<T>() {
    override fun showDialogRegister(email: String?) {
        TODO("Not yet implemented")
    }

    override fun showDialogRestore(email: String?) {
        TODO("Not yet implemented")
    }
}