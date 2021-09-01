package com.ruani.authdagger.mvp.interfaces

interface IOUserDataStorage {
    fun putPassword(password: String)
    fun getPassword(): String?
    fun getEmail(): String?
    fun putEmail(email: String)
    fun existPassword(): Boolean
}