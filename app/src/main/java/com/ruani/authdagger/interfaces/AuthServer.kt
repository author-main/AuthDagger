package com.ruani.authdagger.interfaces

import com.ruani.authdagger.abstract_data.auth_data

interface AuthServer {
    var onAuthServerResult: ((action: auth_data.AuthAction, result: auth_data.AuthValue) -> Unit)?
    fun executRequest(authAction: auth_data.AuthAction, email: String?, password: String?): auth_data.AuthValue
}