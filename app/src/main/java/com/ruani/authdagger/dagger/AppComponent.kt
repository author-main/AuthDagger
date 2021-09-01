package com.ruani.authdagger.dagger

import com.ruani.authdagger.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresenterModule::class])
@Singleton
interface AppComponent {
    fun injectsMainActivity(mainActivity: MainActivity)
}