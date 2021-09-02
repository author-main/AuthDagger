package com.ruani.authdagger.dagger

import com.ruani.authdagger.MainActivity
import com.ruani.authdagger.mvp.Contract
import com.ruani.authdagger.mvp.Model
import com.ruani.authdagger.mvp.Presenter
import com.ruani.authdagger.mvp.TPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresenterModule::class, ModelModule::class])
@Singleton
interface AppComponent {
    fun injectsMainActivity(mainActivity: MainActivity)
    fun injectsModel(model: Model)
    fun injectsPresenter(presenter: Presenter)
}