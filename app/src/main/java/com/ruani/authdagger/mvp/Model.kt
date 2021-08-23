package com.ruani.authdagger.mvp

import android.view.View
import com.ruani.authdagger.abstract_data.AuthData
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.mvp.model_classes.UserDataStorage

class Model: Contract.IModel {
    private val userDataStorage = UserDataStorage()
    override fun putPassword(password: String) {
        userDataStorage.putPassword(password)
    }

    private fun getPassword() = userDataStorage.getPassword()

    override fun getEmail() = userDataStorage.getEmail()

    override fun putEmail(email: String) {
        userDataStorage.putEmail(email)
    }

    override fun viewOnClick(v: View): AuthData.AuthValue {
        TODO("Not yet implemented")
    }

}