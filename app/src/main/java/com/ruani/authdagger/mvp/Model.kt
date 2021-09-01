package com.ruani.authdagger.mvp

import android.view.View
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.TModel
import com.ruani.authdagger.interfaces.AuthServer
import com.ruani.authdagger.mvp.model_classes.FirebaseServer
import com.ruani.authdagger.mvp.model_classes.UserDataStorage

class Model: TModel<AuthServer>() {
    private val userDataStorage = UserDataStorage()

    init {
        attachServer(FirebaseServer())
    }

    override fun putPassword(password: String) {
        userDataStorage.putPassword(password)
    }

    override fun getPassword() = userDataStorage.getPassword()



    override fun getEmail() = userDataStorage.getEmail()

    override fun putEmail(email: String) {
        userDataStorage.putEmail(email)
    }
}