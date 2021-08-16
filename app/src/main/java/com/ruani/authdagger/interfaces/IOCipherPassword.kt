package com.ruani.authdagger.interfaces

interface IOCipherPassword {
    fun decryptPassword(): String?
    fun encryptPassword(password: String)
    fun existPassword(): Boolean
    fun correctPassword(valie: String): Boolean
}