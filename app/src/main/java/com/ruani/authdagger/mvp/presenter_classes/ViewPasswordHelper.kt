package com.ruani.authdagger.mvp.presenter_classes

import android.widget.TextView
import com.ruani.authdagger.*
import com.ruani.authdagger.mvp.classes.LENGTH_PASSWORD
import com.ruani.authdagger.mvp.classes.getColorResource
import kotlinx.coroutines.*

class ViewPasswordHelper(private val symbols: Array<TextView>) {
    private var job: Job? = null
    private val colorSymbol: Int       = getColorResource(R.color.symbol)
    private val colorSymbolActive: Int = getColorResource(R.color.symbolActive)
    private val hidenSymbol = "•"
    private var oldLength = 0
    var onFinish: (() -> Unit)? = null

    fun setValue(value: String?, show: Boolean = true){
        fun isFinish() =
            value?.length == LENGTH_PASSWORD

        fun clearSymbols() {
            symbols.forEach { symbol ->
                symbol.text = hidenSymbol
                symbol.setTextColor(colorSymbol)
            }
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
                        if (isFinish())
                            onFinish?.invoke()
                    }
                }
            } else {
                oldLength = value.length
                for(i in value.indices)
                    symbols[i].setTextColor(colorSymbolActive)
                if (isFinish()) {
                    job?.cancel()
                    job = CoroutineScope(Dispatchers.Main).launch {
                        delay(400)
                        onFinish?.invoke()
                    }
                }
            }
        } else
            oldLength = 0
    }
}