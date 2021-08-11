package com.ruani.authdagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

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

    override fun accessed() {
        TODO("Not yet implemented")
    }

    override fun showError(error: AuthData.AuthValue) {
        TODO("Not yet implemented")
    }
}