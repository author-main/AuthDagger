package com.ruani.authdagger.dagger

import com.ruani.authdagger.mvp.Contract
import com.ruani.authdagger.mvp.Presenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun providePresenter(): Presenter<Contract.IView> {
        return Presenter()
    }

}