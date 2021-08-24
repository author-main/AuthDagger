package com.ruani.authdagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.abstract_data.Contract
import com.ruani.authdagger.mvp.Presenter
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

    override fun onSignin() {
        TODO("Not yet implemented")
    }

    override fun onError(error: auth_data.AuthValue) {
        TODO("Not yet implemented")
    }

    override fun onRegistered() {
        TODO("Not yet implemented")
    }

    override fun onRestored() {
        TODO("Not yet implemented")
    }

    override fun clickView(v: View) {
        when (val tag = v.tag.toString()) {
            "register" -> {

            }
            "restore" -> {

            }
            "delete" -> {
                presenter.changePassword(null)
            }
            "finger" -> {
            } else -> {
                presenter.changePassword(tag)
            }
        }

    }

 /*   override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }*/

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
}