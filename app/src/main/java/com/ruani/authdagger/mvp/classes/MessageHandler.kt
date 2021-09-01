package com.ruani.authdagger.mvp.classes

import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.widget.Toast
import com.ruani.authdagger.R
import com.ruani.authdagger.abstract_data.auth_data

class MessageHandler {
    companion object {
        fun showMessage(message: String) {
            val toast: Toast = Toast.makeText(getAppContext(), message, Toast.LENGTH_SHORT)
            val centeredText: Spannable = SpannableString(message)
            centeredText.setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, message.length - 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            toast.show()
        }

        fun showAuthError(authAction: auth_data.AuthAction, error: auth_data.AuthValue) {
            val idText = if (error == auth_data.AuthValue.ERROR_CONNECTION)
                    R.string.error_connected_internet
                else if (error == auth_data.AuthValue.ERROR_USERDATA)
                    R.string.error_userdata
                else
                    when (authAction) {
                        auth_data.AuthAction.SIGNIN     ->
                            R.string.error_auth
                        auth_data.AuthAction.RESTORE    ->
                            R.string.error_restore
                        auth_data.AuthAction.REGISTER   ->
                            R.string.error_register
                    }
            val text = getStringResource(idText)
            if (!text.isNullOrBlank())
                showMessage(text)
        }
    }

}