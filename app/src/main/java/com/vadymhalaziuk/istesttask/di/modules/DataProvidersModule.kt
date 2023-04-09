package com.vadymhalaziuk.istesttask.di.modules

import com.vadymhalaziuk.istesttask.data.android.AndroidSystemPrefRepositoryImpl
import com.vadymhalaziuk.istesttask.data.local.LocalActionsRepositoryImpl
import com.vadymhalaziuk.istesttask.data.remote.RemoteActionsRepository
import com.vadymhalaziuk.istesttask.domain.repository.AndroidSystemPrefRepository
import com.vadymhalaziuk.istesttask.domain.repository.LocalActionsRepository
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
    abstract fun appPrefsRepository(impl: LocalActionsRepositoryImpl): LocalActionsRepository

    @Binds
    abstract fun remoteActionsRepository(impl: RemoteActionsRepository): com.vadymhalaziuk.istesttask.domain.repository.RemoteActionsRepository
}