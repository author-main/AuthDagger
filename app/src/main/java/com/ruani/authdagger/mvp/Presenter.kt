package com.ruani.authdagger.mvp

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.ruani.authdagger.MainActivity
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.TPresenter
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.mvp.presenter_classes.ViewPasswordHelper

class Presenter<T: Contract.IView>(view: T): TPresenter<T, Model>(view) {
   // private val email: ObservableField<String> = ObservableField()
    //private val password: ObservableField<String> = ObservableField()
    init {
        attachModel(Model())
    }


    /*      password.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
                  override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                      setPassword(password.get())
                  }
              }
          )
      }*/

}