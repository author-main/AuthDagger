package com.ruani.authdagger.mvp.model_classes

import com.ruani.authdagger.getAppContext
import com.ruani.authdagger.interfaces.IOCipherPassword
import java.security.Key
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.cert.Certificate

class CipherPassword: IOCipherPassword {
    private val providerKeyStore: String = "AndroidKeyStore"
    private val alias = getAppContext().packageName
    private var keyStore: KeyStore? = getKeyStore()

    init {
        initKeys()
    }

    override fun decryptPassword(value: String): String {
        TODO("Not yet implemented")
    }

    override fun encryptPassword(value: String): String {
        TODO("Not yet implemented")
    }

    override fun existPassword(): Boolean {
        TODO("Not yet implemented")
    }

    override fun correctPassword(valie: String): Boolean {
        TODO("Not yet implemented")
    }

    private fun initKeys() =
        !(getPrivateKey() == null || getCertificate() == null)

    private fun getPrivateKey() =
        keyStore?.getKey(alias, null)

    private fun getCertificate() =
        keyStore?.getCertificate(alias)

    private fun getKeyStore(): KeyStore?{
        return try {
            val keystore = KeyStore.getInstance(providerKeyStore)
            keystore.load(null)
            keystore
        } catch (ex: KeyStoreException){
            null
        }
    }
}