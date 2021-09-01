package com.ruani.authdagger.mvp

import com.ruani.authdagger.mvp.interfaces.AuthServer
import com.ruani.authdagger.mvp.interfaces.FingerPrint
import com.ruani.authdagger.mvp.interfaces.IOCipherPassword
import com.ruani.authdagger.mvp.interfaces.IOUserDataStorage
import com.ruani.authdagger.mvp.model_classes.FirebaseServer
import com.ruani.authdagger.mvp.model_classes.UserDataStorage
import com.ruani.authdagger.mvp.model_classes.AuthFingerPrint
import com.ruani.authdagger.mvp.model_classes.CipherPassword

class Model: TModel<AuthServer, FingerPrint<Contract.IView>, IOUserDataStorage>() {
    init {
        attachServer(FirebaseServer())
        attachFingerPrint(AuthFingerPrint())
        attachStorage(UserDataStorage())
    }
}