package com.ruani.authdagger.mvp.presenter_classes

import android.widget.TextView
import com.ruani.authdagger.AUTHFINGER_COMPLETE
import com.ruani.authdagger.R
import com.ruani.authdagger.getAppContext
import com.ruani.authdagger.getColorFromTheme

class viewPasswordHelper(private val symbols: Array<TextView>) {
    var onChangePassword: (() -> Unit)? = null
    private val colorSymbol = getColorFromTheme(android.R.attr.textColorSecondary)
    private val colorSymbolActive = getColorFromTheme(R.attr.colorAccent)
    val hidenSymbol = "•"

    private fun setSymbolColor(index: Int, active: Boolean = false){
        val color =
            if (active)
                colorSymbolActive
            else
                colorSymbol
        symbols[index].setTextColor(color)
    }

    fun fillSymbols(active: Boolean = false) {
        val color =
            if (active)
                colorSymbolActive
            else
                colorSymbol
        symbols.forEach { symbol->
            symbol.setTextColor(color)
        }
    }

    fun changeSymbol(index: Int, value: String?){
        if (value.isNullOrBlank())
            setSymbolColor(index)
        else {
            if (value == AUTHFINGER_COMPLETE) {
                fillSymbols(true)
                return
            }
            symbols[index].text = value
        }

        if (index == 3) {
            if (value != null && onChangePassword != null){
                onChangePassword?.invoke()
            }
        } else {

        }
    }
}