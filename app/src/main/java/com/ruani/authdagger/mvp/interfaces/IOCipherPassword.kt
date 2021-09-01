package com.ruani.authdagger.mvp.interfaces

interface IOCipherPassword {
    fun decryptPassword(value: String): String?
    fun encryptPassword(value: String): String?
}