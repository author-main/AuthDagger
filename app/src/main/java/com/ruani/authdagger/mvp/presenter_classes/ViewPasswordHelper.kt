package com.ruani.authdagger.mvp.presenter_classes

import android.graphics.Color
import android.widget.TextView
import com.ruani.authdagger.*
import com.ruani.authdagger.abstract_data.auth_data

class ViewPasswordHelper(private val symbols: Array<TextView>) {
    private val colorSymbol: Int       = getColorResource(R.color.symbol)
    private val colorSymbolActive: Int = getColorResource(R.color.symbolActive)
    val hidenSymbol = "•"
    private var indexView = -1

    private fun setSymbolColor(index: Int, active: Boolean = false){
        val color =
            if (active)
                colorSymbolActive
            else
                colorSymbol
        symbols[index].setTextColor(color)
    }

    private fun fillSymbols(active: Boolean = false) {
        val color =
            if (active)
                colorSymbolActive
            else
                colorSymbol
        symbols.forEach { symbol->
            symbol.setTextColor(color)
        }
    }

    fun changeSymbol(value: String?){
        if (value.isNullOrBlank()) {
            if (indexView > -1) {
                setSymbolColor(indexView)
                indexView--
            }
        }
        else {
            if (value == AUTHFINGER_COMPLETE) {
                fillSymbols(true)
                return
            }

            if (indexView == LENGTH_PASSWORD - 1) return // <- удалить

            indexView++
            setSymbolColor(indexView, true)
        }
    }

    fun changeSymbolsColor(value: String?){
        fillSymbols()
        if (value.isNullOrBlank()) {
            indexView = -1
        }
        else {
            indexView = value.length - 1
            value.forEachIndexed { index, _ ->
                setSymbolColor(index, true)
            }
        }
    }
}