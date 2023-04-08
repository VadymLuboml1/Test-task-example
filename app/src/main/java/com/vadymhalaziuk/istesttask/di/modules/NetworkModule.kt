package com.vadymhalaziuk.istesttask.di.modules

import com.google.gson.Gson
import com.vadymhalaziuk.istesttask.data.remote.ActionsRestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun gson(): Gson = Gson()

    @Provides
    fun retrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://s3-us-west-2.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun actionsRestApi(retrofit: Retrofit): ActionsRestApi =
        retrofit.create(ActionsRestApi::class.java)

}