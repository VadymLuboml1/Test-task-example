package com.vadymhalaziuk.istesttask.di.modules

import android.app.Application
import androidx.room.Room
import com.vadymhalaziuk.istesttask.data.local.source.SAVED_ACTIONS_DB_NAMESPACE
import com.vadymhalaziuk.istesttask.data.local.source.SavedActionsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStoragesModule {

    @Singleton
    @Provides
    fun savedActionsDatabase(context: Application): SavedActionsDatabase =
        Room.databaseBuilder(
            context = context,
            klass = SavedActionsDatabase::class.java,
            name = SAVED_ACTIONS_DB_NAMESPACE
        ).build()
}