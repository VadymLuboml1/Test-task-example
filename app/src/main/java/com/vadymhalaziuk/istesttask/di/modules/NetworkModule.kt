package com.vadymhalaziuk.istesttask.di.modules

import com.google.gson.Gson
import com.vadymhalaziuk.istesttask.data.remote.source.ActionsRestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun gson(): Gson = Gson()

    @Singleton
    @Provides
    fun retrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://s3-us-west-2.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun actionsRestApi(retrofit: Retrofit): ActionsRestApi =
        retrofit.create(ActionsRestApi::class.java)

}