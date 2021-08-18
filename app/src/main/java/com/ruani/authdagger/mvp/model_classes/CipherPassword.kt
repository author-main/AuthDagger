package com.ruani.authdagger.mvp.model_classes

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.ruani.authdagger.getAppContext
import com.ruani.authdagger.interfaces.IOCipherPassword
import java.security.*
import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec

class CipherPassword: IOCipherPassword {
    private val providerKeyStore: String = "AndroidKeyStore"
    private val alias = getAppContext().packageName
    private var keyStore: KeyStore? = getKeyStore()

    init {
        if (!initKeys())
            generateKeys()
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

    private fun generateKeys(){
        if (keyStore == null)
            return
        try {
            val spec: AlgorithmParameterSpec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_DECRYPT
            )
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build()
            try {
                val kpGenerator =
                    KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA, providerKeyStore)
                kpGenerator.initialize(spec)
                kpGenerator.generateKeyPair()
            } catch (ex: NoSuchAlgorithmException){}
        } catch (e: Exception) {}
    }

}