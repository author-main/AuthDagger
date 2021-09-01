package com.ruani.authdagger.dagger

import com.ruani.authdagger.MainActivity
import dagger.Component

@Component(modules = [PresenterModule::class])
interface AppComponent {
    fun injectsMainActivity(mainActivity: MainActivity)
}