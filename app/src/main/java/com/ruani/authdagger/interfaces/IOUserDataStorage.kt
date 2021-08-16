package com.ruani.authdagger.interfaces

interface IOUserDataStorage {
    fun putPassword(password: String)
    fun getPassword(): String?
    fun getEmail(): String?
    fun putEmail(email: String)
}