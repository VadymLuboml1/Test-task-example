package com.vadymhalaziuk.istesttask.di.modules

import com.vadymhalaziuk.istesttask.data.AppPrefsRepositoryImpl
import com.vadymhalaziuk.istesttask.data.android.AndroidSystemPrefRepositoryImpl
import com.vadymhalaziuk.istesttask.domain.repository.AndroidSystemPrefRepository
import com.vadymhalaziuk.istesttask.domain.repository.AppPrefsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataProvidersModule {

    @Binds
    abstract fun androidSystemPrefRepository(impl: AndroidSystemPrefRepositoryImpl): AndroidSystemPrefRepository

    @Binds
    abstract fun appPrefsRepository(impl: AppPrefsRepositoryImpl): AppPrefsRepository

    @Binds
    abstract fun remoteActionsRepository(impl: com.vadymhalaziuk.istesttask.data.RemoteActionsRepository): com.vadymhalaziuk.istesttask.domain.repository.RemoteActionsRepository
}