package com.ruani.authdagger.mvp.model_classes

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.mvp.interfaces.AuthServer
import com.ruani.authdagger.helpers.connectedInternet

class FirebaseServer: AuthServer {
    private val instance = FirebaseAuth.getInstance()
    override var onAuthServerResult: ((action: auth_data.AuthAction, result: auth_data.AuthValue) -> Unit)? = null
    private var email   : String? = null
    private var password   : String? = null

    override fun executeRequest(
        authAction: auth_data.AuthAction,
        email: String?,
        password: String?
    ){
        if (!connectedInternet()) {
            onAuthServerResult?.invoke(authAction, auth_data.AuthValue.ERROR_CONNECTION)
            return
        }
        this.email = email
        this.password = password
        var errorData = false
        when (authAction) {
            auth_data.AuthAction.SIGNIN -> {
                if (!email.isNullOrBlank() && !password.isNullOrBlank())
                    signin(email, password)
                else
                    errorData = true

            }
            auth_data.AuthAction.REGISTER -> {
                if (!email.isNullOrBlank() && !password.isNullOrBlank())
                    register(email, password)
                else
                    errorData = true
            }
            auth_data.AuthAction.RESTORE -> {
                if (!email.isNullOrBlank())
                    restore(email)
                else
                    errorData = true
            }
        }
        if (errorData)
            onAuthServerResult?.invoke(authAction, auth_data.AuthValue.ERROR_USERDATA)

    }

    private fun signin(email: String, password: String) {
        instance.signInWithEmailAndPassword(email, password+"0").addOnCompleteListener { task ->
            finishTask(task, auth_data.AuthAction.SIGNIN)
        }
    }

    private fun register(email: String, password: String) {
        instance.createUserWithEmailAndPassword(email, password+"0").addOnCompleteListener {task ->
            finishTask(task, auth_data.AuthAction.REGISTER)
        }
    }

    private fun restore(email: String) {
        instance.currentUser?.sendEmailVerification()
        instance.sendPasswordResetEmail(email).addOnCompleteListener {task ->
            finishTask(task, auth_data.AuthAction.RESTORE)
        }
    }

    private fun finishTask(task: Task<*>, action: auth_data.AuthAction) {
        if (task.isSuccessful)
            onAuthServerResult?.invoke(action, auth_data.AuthValue.COMPLETE)
        else {
            email = null
            password = null
            onAuthServerResult?.invoke(action, auth_data.AuthValue.ERROR)
        }
    }

    override fun getServerEmail(): String? =
        email

    override fun getServerPassword(): String? {
        val value = password
        password = null
        return value
    }
 }