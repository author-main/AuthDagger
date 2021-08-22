package com.ruani.authdagger.mvp.model_classes

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA
import android.util.Base64
import com.ruani.authdagger.getAppContext
import com.ruani.authdagger.interfaces.IOCipherPassword
import java.security.*
import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher

class CipherPassword: IOCipherPassword {
    private val providerKeyStore: String = "AndroidKeyStore"
    private val alias = getAppContext().packageName
    private val keyStore: KeyStore? = getKeyStore()

    init {
        if (!initKeys())
            generateKeys()
    }

    override fun decryptPassword(value: String): String? {
        val cipher = getDecryptCipher()
        return cipher?.let{cipher_->
            val passwordBase64: ByteArray = Base64.decode(value, Base64.DEFAULT)
            val password = cipher_.doFinal(passwordBase64) ?: return null
            password.toString(Charsets.UTF_8)
        }
    }

    override fun encryptPassword(value: String): String? {
        val passwordUtf = value.toByteArray(Charsets.UTF_8)
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        val publicKey = keyStore?.getCertificate(alias)?.publicKey
        return publicKey?.let{key_->
            cipher.init(Cipher.ENCRYPT_MODE, key_)
            val encrypted = cipher.doFinal(passwordUtf)
            Base64.encodeToString(encrypted, Base64.DEFAULT)
        }
    }

    /*override fun correctPassword(value: String): Boolean {
        val password = decryptPassword(value)
        return password?.equals(value, false) ?: false
    }*/

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
        keyStore?.let {
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
                } catch (ex: NoSuchAlgorithmException) {
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun getDecryptCipher(): Cipher? {
        return keyStore?.let { keystore_ ->
            try {
                val privateKey: PrivateKey = keystore_.getKey(alias, null) as PrivateKey
                val cipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
                cipher.init(Cipher.DECRYPT_MODE, privateKey)
                cipher
            } catch (ex: java.lang.Exception) {
                null
            }
        }
    }
}