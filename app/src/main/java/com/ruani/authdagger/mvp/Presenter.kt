package com.ruani.authdagger.mvp

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.ruani.authdagger.MainActivity
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.TPresenter
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.mvp.presenter_classes.ViewPasswordHelper

class Presenter<T: Contract.IView>: TPresenter<T, Model>() {
    private var viewPasswordHelper: ViewPasswordHelper? = null
    init {
        attachModel(Model())
    }

    override fun attachView(v: T) {
        super.attachView(v)
        val symbols = getView()?.getSymbolViews()
        symbols?.let {
            viewPasswordHelper = ViewPasswordHelper(it)
        }
    }

    override fun changePassword(symbol: String?) {
        super.changePassword(symbol)
        viewPasswordHelper?.changeSymbol(symbol)
    }

    /*      password.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
                  override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                      setPassword(password.get())
                  }
              }
          )
      }*/

}