package com.ruani.authdagger.mvp.presenter_classes

import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.interfaces.AuthDialog

const val DIALOG_RESTORE  = 0
const val DIALOG_REGISTER = 1

class FirebaseDialog<T: Contract.IView>: AuthDialog<T>() {
    override fun showDialogRegister(email: String?) {
        show(DIALOG_REGISTER, email)
    }

    override fun showDialogRestore(email: String?) {
        show(DIALOG_RESTORE, email)
    }

    private fun show(value: Int, email: String?){
        if (email.isNullOrBlank()) {
            return
        }
        if (value == DIALOG_REGISTER) {
        }
        if (value == DIALOG_RESTORE) {
        }
    }
}