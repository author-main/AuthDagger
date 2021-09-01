package com.ruani.authdagger.mvp.presenter_classes

import android.widget.TextView
import com.ruani.authdagger.*
import kotlinx.coroutines.*

class ViewPasswordHelper(private val symbols: Array<TextView>) {
    private var job: Job? = null
    private val colorSymbol: Int       = getColorResource(R.color.symbol)
    private val colorSymbolActive: Int = getColorResource(R.color.symbolActive)
    private val hidenSymbol = "•"
    private var oldLength = 0
    var onFinish: (() -> Unit)? = null

    fun setValue(value: String?, show: Boolean = true){
        fun clearSymbols() {
            symbols.forEach { symbol ->
                symbol.text = hidenSymbol
                symbol.setTextColor(colorSymbol)
            }
        }
        fun finish(){
            onFinish?.invoke()
        }
        clearSymbols()

        if (!value.isNullOrBlank()) {
            if (show) {
                val deleteSym = oldLength > value.length
                oldLength = value.length
                val sym   = value.last()
                val index = value.length - 1
                for (i in value.indices) {
                    symbols[i].text = hidenSymbol
                    symbols[i].setTextColor(colorSymbolActive)
                }
                job?.cancel()
                if (!deleteSym) {
                    job = CoroutineScope(Dispatchers.Main).launch {
                        symbols[index].text = sym.toString()
                        delay(400)
                        symbols[index].text = hidenSymbol
                        if (value.length == LENGTH_PASSWORD) {
                            finish()
                        }
                    }

                }


            } else {
                oldLength = value.length
                for(i in value.indices)
                    symbols[i].setTextColor(colorSymbolActive)

                if (value.length == LENGTH_PASSWORD) {
                    job?.cancel()
                    job = CoroutineScope(Dispatchers.Main).launch {
                        delay(400)
                        finish()
                    }
                }
            }
        } else
            oldLength = 0
    }
}