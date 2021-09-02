package com.ruani.authdagger.dagger

import com.ruani.authdagger.mvp.Contract
import com.ruani.authdagger.mvp.Model
import com.ruani.authdagger.mvp.Presenter
import com.ruani.authdagger.mvp.interfaces.AuthServer
import com.ruani.authdagger.mvp.model_classes.AuthFingerPrint
import com.ruani.authdagger.mvp.model_classes.FirebaseServer
import com.ruani.authdagger.mvp.model_classes.UserDataStorage
import com.ruani.authdagger.mvp.presenter_classes.Dyer
import com.ruani.authdagger.mvp.presenter_classes.FirebaseDialog
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providePresenter() = Presenter()

    @Provides
    fun provideModule() = Model()

    @Provides
    fun provideDialogs() = FirebaseDialog<Contract.IView>()
}