package com.ruani.authdagger.interfaces

interface IOCipherPassword {
    fun getPassword(): String?
    fun setPassword(password: String)
    fun existPassword(): Boolean
    fun correctPassword(valie: String): Boolean
}