package com.ruani.authdagger.mvp.model_classes

import com.ruani.authdagger.getAppContext
import com.ruani.authdagger.interfaces.IOCipherPassword

class CipherPassword: IOCipherPassword {
    private val providerKeyStore: String = "AndroidKeyStore"
    private val alias = getAppContext().packageName

    override fun decryptPassword(): String? {
        TODO("Not yet implemented")
    }

    override fun encryptPassword(password: String) {
        TODO("Not yet implemented")
    }

    override fun existPassword(): Boolean {
        TODO("Not yet implemented")
    }

    override fun correctPassword(valie: String): Boolean {
        TODO("Not yet implemented")
    }
}