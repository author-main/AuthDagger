package com.ruani.authdagger.dagger

import com.ruani.authdagger.MainActivity
import com.ruani.authdagger.mvp.Contract
import com.ruani.authdagger.mvp.Presenter
import dagger.Component

@Component(modules = [PresenterModule::class])
interface AppComponent {
    fun injectsMainActivity(mainActivity: MainActivity)
}