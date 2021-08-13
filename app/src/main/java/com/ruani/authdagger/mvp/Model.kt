package com.ruani.authdagger.mvp

import android.view.View
import com.ruani.authdagger.abstractData.AuthData
import com.ruani.authdagger.abstractData.Contract

class Model: Contract.IModel {
    override fun putPassword(password: String) {
        TODO("Not yet implemented")
    }

    override fun getEmail(): String {
        TODO("Not yet implemented")
    }

    override fun putEmail(email: String) {
        TODO("Not yet implemented")
    }

    override fun viewOnClick(v: View): AuthData.AuthValue {
        TODO("Not yet implemented")
    }
}