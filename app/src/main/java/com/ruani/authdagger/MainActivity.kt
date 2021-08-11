package com.ruani.authdagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.ruani.authdagger.abstractData.AuthData
import com.ruani.authdagger.abstractData.Contract

class MainActivity : AppCompatActivity(), Contract.IView {
    companion object{
        private fun setNightMode() {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setNightMode()
    }

    override fun onSignin() {
        TODO("Not yet implemented")
    }

    override fun onError(error: AuthData.AuthValue) {
        TODO("Not yet implemented")
    }

    override fun onRegistered() {
        TODO("Not yet implemented")
    }

    override fun onRestored() {
        TODO("Not yet implemented")
    }
}