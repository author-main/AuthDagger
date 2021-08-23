package com.ruani.authdagger.mvp

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.abstract_data.TPresenter

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