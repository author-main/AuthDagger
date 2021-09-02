package com.ruani.authdagger.dagger

import com.ruani.authdagger.mvp.Contract
import com.ruani.authdagger.mvp.model_classes.AuthFingerPrint
import com.ruani.authdagger.mvp.model_classes.FirebaseServer
import com.ruani.authdagger.mvp.model_classes.UserDataStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModelModule {
    @Provides
    @Singleton
    fun provideServer() = FirebaseServer()

    @Provides
    fun provideScaner() = AuthFingerPrint<Contract.IView>()

    @Provides
    fun provideStorage() = UserDataStorage()
}