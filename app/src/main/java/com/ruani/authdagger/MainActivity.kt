package com.ruani.authdagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.mvp.Presenter
import com.ruani.authdagger.mvp.classes.MessageHandler
import com.ruani.authdagger.mvp.presenter_classes.FirebaseDialog
import com.ruani.authdagger.mvp.presenter_classes.ViewPasswordHelper

class MainActivity : AppCompatActivity(), Contract.IView {
    private lateinit var dataBinding: com.ruani.authdagger.databinding.ActivityMainBinding
    private lateinit var presenter: Presenter<Contract.IView>
    companion object{
        private fun setNightMode() {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        }
    }
    init {
        setNightMode()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        initData()
    }

    private fun initData(){
        presenter = Presenter()
        dataBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        dataBinding.eventhandler = presenter
        presenter.attachView(this)
    }

    /*override fun clickView(v: auth_data.AuthButton) {
     /*   if (v == auth_data.AuthButton.BUTTON_REGISTER)
            presenter.showDialogProgress()*/

    }*/

    override fun onResultAuth(authAction: auth_data.AuthAction, authValue: auth_data.AuthValue) {
        if (authValue != auth_data.AuthValue.COMPLETE)
            MessageHandler.showAuthError(authAction, authValue)
        else {

            val value = when (authAction){
                auth_data.AuthAction.RESTORE ->
                    getStringResource(R.string.dlgrest_success)
                auth_data.AuthAction.REGISTER ->
                    getStringResource(R.string.dlgreg_success)
                else ->
                    null
            }


            if (!value.isNullOrBlank())
                MessageHandler.showMessage(value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun getSymbolViews() =
        arrayOf(dataBinding.textViewSymbol0,
            dataBinding.textViewSymbol1,
            dataBinding.textViewSymbol2,
            dataBinding.textViewSymbol3,
            dataBinding.textViewSymbol4)

    override fun enabledFingerPrint(value: Boolean?) {
        dataBinding.buttonFinger.isEnabled = value ?: false
    }
}