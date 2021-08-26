package com.ruani.authdagger.mvp.model_classes

import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.connectedInternet
import com.ruani.authdagger.interfaces.AuthServer

class FirebaseServer: AuthServer {
    private val instance = FirebaseAuth.getInstance()
    override var onAuthServerResult: ((action: auth_data.AuthAction, result: auth_data.AuthValue) -> Unit)? = null

    override fun executRequest(
        authAction: auth_data.AuthAction,
        email: String?,
        password: String?
    ){
        if (!connectedInternet()) {
            onAuthServerResult?.invoke(authAction, auth_data.AuthValue.ERROR_CONNECTION)
            return
        }
        when (authAction) {
            auth_data.AuthAction.SIGNIN -> {
                if (!email.isNullOrBlank() && !password.isNullOrBlank())
                    signin(email, password)
            }
            auth_data.AuthAction.REGISTER -> {
                if (!email.isNullOrBlank() && !password.isNullOrBlank())
                    register(email, password)
            }
            auth_data.AuthAction.RESTORE -> {
                if (!email.isNullOrBlank())
                    restore(email)
            }
        }
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
            /*if (authResultErrorConnection(AuthAction.RESTORE))
                return@addOnCompleteListener
            if (task.isSuccessful)
                authResultListener?.onAutentificationComplete(AuthAction.RESTORE, AuthValue.SUCCESSFUL)
            else
                authResultListener?.onAutentificationComplete(AuthAction.RESTORE, AuthValue.ERROR_RESTORE)*/
        }
    }

    private fun finishTask(task: Task<*>, action: auth_data.AuthAction) {
        if (task.isSuccessful)
            onAuthServerResult?.invoke(action, auth_data.AuthValue.COMPLETE)
        else
            onAuthServerResult?.invoke(action, auth_data.AuthValue.ERROR)
    }


  /*  private fun getErrorAuth(ex: Exception?): auth_data.AuthValue {
        ex?.let { exception ->
            if (exception is FirebaseNetworkException)
                return auth_data.AuthValue.ERROR_CONNECTION
            if (exception is FirebaseAuthUserCollisionException)
                return auth_data.AuthValue.ERROR_ALREADY_EMAIL
            if (exception is FirebaseAuthException)
                return auth_data.AuthValue.ERROR_USER_DATA
        }
        return auth_data.AuthValue.ERROR_AUTH_SERVICE
    }*/

}