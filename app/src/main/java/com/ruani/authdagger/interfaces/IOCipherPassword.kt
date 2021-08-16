package com.ruani.authdagger.interfaces

interface IOCipherPassword {
    fun decryptPassword(value: String): String
    fun encryptPassword(value: String): String
    fun existPassword(): Boolean
    fun correctPassword(valie: String): Boolean
}