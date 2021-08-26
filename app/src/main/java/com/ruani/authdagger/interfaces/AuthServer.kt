package com.ruani.authdagger.interfaces

import com.ruani.authdagger.abstract_data.auth_data

interface AuthServer {
    var onAuthServerResult: ((action: auth_data.AuthAction, result: auth_data.AuthValue) -> Unit)?
    fun executeRequest(authAction: auth_data.AuthAction, email: String?, password: String?)
    fun getAuthEmail(): String?
    fun getAuthPassword(): String?
}