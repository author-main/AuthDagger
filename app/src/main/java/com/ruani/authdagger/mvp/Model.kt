package com.ruani.authdagger.mvp

import com.ruani.authdagger.AuthApplication
import com.ruani.authdagger.mvp.interfaces.AuthServer
import com.ruani.authdagger.mvp.interfaces.FingerPrint
import com.ruani.authdagger.mvp.interfaces.IOUserDataStorage
import com.ruani.authdagger.mvp.model_classes.FirebaseServer
import com.ruani.authdagger.mvp.model_classes.UserDataStorage
import com.ruani.authdagger.mvp.model_classes.AuthFingerPrint
import javax.inject.Inject

class Model: TModel<AuthServer, FingerPrint<Contract.IView>, IOUserDataStorage>() {
    @Inject lateinit var authServer: FirebaseServer
    @Inject lateinit var scanerFinger: AuthFingerPrint<Contract.IView>
    @Inject lateinit var storageData: UserDataStorage

    init {
        AuthApplication.getComponent().injectsModel(this)
        attachServer(authServer)
        attachFingerPrint(scanerFinger)
        attachStorage(storageData)
    }
}