package com.ruani.authdagger.mvp.model_classes

import com.ruani.authdagger.getAppContext
import com.ruani.authdagger.interfaces.IOCipherPassword
import java.security.KeyStore

class CipherPassword: IOCipherPassword {
    private val providerKeyStore: String = "AndroidKeyStore"
    private val alias = getAppContext().packageName

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

    private fun initKeys(): Boolean{
        val keyStore = getKeyStore() ?: return false
        return try {
            val privateKey = keyStore.getKey(alias, null)
            val certificate = keyStore.getCertificate(alias)
            privateKey !=null && certificate?.publicKey != null
        } catch (e: Exception){
            false
        }
    }

    private fun getKeyStore(): KeyStore?{
        var keyStore: KeyStore? = null
        return try{
            keyStore = KeyStore.getInstance(providerKeyStore)
            keyStore?.load(null)
            keyStore
        } catch (e: Exception) {
            keyStore?.deleteEntry(alias)
            null
        }
    }
}