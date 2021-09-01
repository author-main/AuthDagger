package com.ruani.authdagger

import android.app.Application
import android.content.Context
import com.ruani.authdagger.dagger.AppComponent
import com.ruani.authdagger.dagger.DaggerAppComponent


class AuthApplication: Application() {
    init {
        instance = this
    }

    companion object {
        private lateinit var component: AppComponent
        private var instance: AuthApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
        fun getComponent(): AppComponent {
            return component
        }
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
    }



}