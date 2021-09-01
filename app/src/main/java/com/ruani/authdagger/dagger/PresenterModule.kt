package com.ruani.authdagger.dagger

import com.ruani.authdagger.mvp.Contract
import com.ruani.authdagger.mvp.Presenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providePresenter() = Presenter<Contract.IView>()
}