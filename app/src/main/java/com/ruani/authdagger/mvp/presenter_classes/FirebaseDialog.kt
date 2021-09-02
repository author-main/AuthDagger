package com.ruani.authdagger.mvp.presenter_classes

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.ruani.authdagger.R
import com.ruani.authdagger.mvp.Contract
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.helpers.getStringResource
import com.ruani.authdagger.mvp.interfaces.AuthDialog
import com.ruani.authdagger.helpers.validateMail

const val DIALOG_RESTORE  = 0
const val DIALOG_REGISTER = 1
const val DIALOG_PROGRESS = 2

class FirebaseDialog<T: Contract.IView>: AuthDialog<T>() {
    class DialogProgress(context: Context) : Dialog(context) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_progress)
            setCancelable(false)
            window?.let {
                val layoutParams = WindowManager.LayoutParams()
                setContentView(R.layout.dialog_progress)
                layoutParams.copyFrom(it.attributes)
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                it.attributes = layoutParams
                it.setBackgroundDrawableResource(android.R.color.transparent)
                it.setDimAmount(0F)
            }
        }


    }

    inner class DialogRegister(context: Context) : Dialog(context, R.style.Dialog) {
        private var email: String? = null
        private lateinit var editEmail: EditText
        private lateinit var editPassword: EditText
        private lateinit var editConfirmPassword: EditText

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_register)
            setTitle(R.string.dlgreg_authentication_title)
            editEmail = findViewById(R.id.editTextEmail)
            editEmail.setText(email)
            editPassword = findViewById(R.id.editTextPassword)
            editConfirmPassword = findViewById(R.id.editTextConfirmPassword)
            findViewById<Button>(R.id.buttonOkReg)?.setOnClickListener {
                perform()
            }
            findViewById<Button>(R.id.buttonCancelReg)?.setOnClickListener {
                dismiss()
            }
        }

        fun setEmail(value: String?) {
            email = value ?: ""
        }

        private fun perform() {
            fun validateEmail(): Boolean {
                val isCorrect = validateMail(editEmail.text.toString())
                if (!isCorrect)
                    editEmail.error = getStringResource(R.string.incorrect_email)
                return isCorrect
            }

            fun validatePassword(): Boolean {
                val password = editPassword.text.toString()
                val isCorrect = !(password.isBlank() || password.length < 5)
                if (!isCorrect)
                    editPassword.error = getStringResource(R.string.dlgreg_error_password)
                return isCorrect
            }

            fun validateConfirmPassword(): Boolean {
                val password = editPassword.text.toString()
                val passwordConfirm = editConfirmPassword.text.toString()
                val isCorrect = !(passwordConfirm.isBlank() || password != passwordConfirm)
                if (!isCorrect)
                    editConfirmPassword.error =
                        getStringResource(R.string.dlgreg_error_passwordcheck)
                return isCorrect
            }
            editEmail.error = null
            editPassword.error = null
            editConfirmPassword.error = null
            if (!validateEmail() || !validatePassword() || !validateConfirmPassword())
                return
            dismiss()
            onDialogResult?.invoke(
                auth_data.AuthAction.REGISTER,
                editEmail.text.toString(),
                editPassword.text.toString()
            )
        }
    }

    inner class DialogRestore(context: Context) : Dialog(context, R.style.Dialog) {
        private var email: String? = null
        private lateinit var editEmail: EditText

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_restore)
            setTitle(R.string.dlgrest_title)
            editEmail = findViewById(R.id.editTextEmailRestore)
            editEmail.setText(email)
            findViewById<Button>(R.id.buttonOkRestore)?.setOnClickListener {
                perform()
            }
            findViewById<Button>(R.id.buttonCancelRestore)?.setOnClickListener {
                dismiss()
            }
        }

        fun setEmail(value: String?) {
            email = value ?: ""
        }

        private fun perform() {
            fun validateEmail(): Boolean {
                val isCorrect = validateMail(editEmail.text.toString())
                if (!isCorrect)
                    editEmail.error = getStringResource(R.string.incorrect_email)
                return isCorrect
            }
            editEmail.error = null
            if (!validateEmail())
                return
            dismiss()
            onDialogResult?.invoke(auth_data.AuthAction.RESTORE, editEmail.text.toString(), null)
        }
    }

    private var dialogProgress: DialogProgress? = null

    override fun showDialogRegister(email: String?) {
        show(DIALOG_REGISTER, email)
    }

    override fun showDialogRestore(email: String?) {
        show(DIALOG_RESTORE, email)
    }

    override fun showDialogProgress() {
        show(DIALOG_PROGRESS)
    }

    override fun hideDialogProgress() {
            dialogProgress?.dismiss()
    }

    private fun show(value: Int, email: String? = null) {
        if (value == DIALOG_PROGRESS) {
            dialogProgress = DialogProgress(getContext()!!)
            dialogProgress?.show()
            return
        }

        when (value) {
            DIALOG_REGISTER -> {
                val dialogRegister = DialogRegister(getContext()!!)
                dialogRegister.setEmail(email)
                dialogRegister.show()
            }
            DIALOG_RESTORE -> {
                val dialogRestore = DialogRestore(getContext()!!)
                dialogRestore.setEmail(email)
                dialogRestore.show()
            }
        }

    }
}

/*override fun show() {
            super.show()
            window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }*/