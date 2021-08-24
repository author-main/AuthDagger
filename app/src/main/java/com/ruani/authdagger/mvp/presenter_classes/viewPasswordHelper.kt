package com.ruani.authdagger.mvp.presenter_classes

import android.widget.TextView

class viewPasswordHelper(private val symbols: Array<TextView>) {
    var onChangePassword: ((password: String) -> Unit)? = null
    fun changeData(index: Int, value: String?){
        if (value.isNullOrBlank())
            symbols[index].text = ""
        else
            symbols[index].text = value
        if (onChangePassword != null && index == 3 && value != null) {
                var password = ""
                symbols.forEach {symbol->
                    password += symbol
                }
                onChangePassword?.invoke(password)
        }
    }
}