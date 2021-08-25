package com.ruani.authdagger.mvp.model_classes

import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
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
    ) {
        if (!connectedInternet()) {
            onAuthServerResult?.invoke(authAction, auth_data.AuthValue.ERROR_CONNECTION)
            return
        }
        when (authAction) {
            auth_data.AuthAction.SIGNIN -> {
                if (!email.isNullOrBlank() && !password.isNullOrBlank())
                    signIn(email, password)
            }
            auth_data.AuthAction.REGISTER -> {
                if (!email.isNullOrBlank() && !password.isNullOrBlank())
                    registerUser(email, password)
            }
            auth_data.AuthAction.RESTORE -> {
                if (!email.isNullOrBlank())
                    restoreUser(email)
            }
        }
    }

    private fun signIn(email: String, password: String) {
      /*  instance.signInWithEmailAndPassword(email, password+"0").addOnCompleteListener { task ->
            resultTask(task, AuthAction.SIGNIN)
        }*/
    }

    private fun registerUser(email: String, password: String) {
       /* instance.createUserWithEmailAndPassword(email, password+"0").addOnCompleteListener {task ->
            resultTask(task, AuthAction.REGISTER)
        }*/
    }

    private fun restoreUser(email: String) {
       /* instance.currentUser?.sendEmailVerification()
        instance.sendPasswordResetEmail(email).addOnCompleteListener {task ->
            if (authResultErrorConnection(AuthAction.RESTORE))
                return@addOnCompleteListener
            if (task.isSuccessful)
                authResultListener?.onAutentificationComplete(AuthAction.RESTORE, AuthValue.SUCCESSFUL)
            else
                authResultListener?.onAutentificationComplete(AuthAction.RESTORE, AuthValue.ERROR_RESTORE)
        }*/
    }

    private fun finalTask(task: Task<AuthResult>, action: auth_data.AuthAction) {

    }


}