package com.ruani.authdagger.interfaces

import com.ruani.authdagger.abstract_data.auth_data

interface AuthDialog {
    var onDialogResult:((action: auth_data.AuthAction, email: String, password: String) -> Unit)?
    fun showDialogRegister(email: String?)
    fun showDialogRestore (email: String?)
}