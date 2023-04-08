package com.vadymhalaziuk.istesttask.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.vadymhalaziuk.istesttask.data.AppPrefsRepository
import com.vadymhalaziuk.istesttask.data.RemoteActionsRepository
import com.vadymhalaziuk.istesttask.data.android.AndroidSystemPrefRepository
import com.vadymhalaziuk.istesttask.data.local.AppSharedPrefStorage
import com.vadymhalaziuk.istesttask.data.mapper.ActionDataLocalMapper
import com.vadymhalaziuk.istesttask.data.mapper.ActionsDataMapper
import com.vadymhalaziuk.istesttask.data.remote.ActionsRestApi
import com.vadymhalaziuk.istesttask.domain.*
import com.vadymhalaziuk.istesttask.ui.ActionsViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ad-hoc for di
object GlobalFactory : ViewModelProvider.Factory {

    private val gson by lazy {
        Gson()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://s3-us-west-2.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val actionsRestApi by lazy {
        retrofit.create(ActionsRestApi::class.java)
    }

    private val appSharedPrefStorage by lazy {
        AppSharedPrefStorage(
            context = appContext,
            gson = gson,
        )
    }

    private val appPrefsRepository by lazy {
        AppPrefsRepository(
            storage = appSharedPrefStorage,
            mapper = ActionDataLocalMapper(),
        )
    }

    private val remoteActionsRepository by lazy {
        RemoteActionsRepository(
            api = actionsRestApi,
            mapper = ActionsDataMapper(),
        )
    }

    private val androidSystemPrefRepository by lazy {
        AndroidSystemPrefRepository(
            appContext
        )
    }

    private val ioDispacher = Dispatchers.IO

    private val filterActionAccordingToLocalUseCase by lazy {
        FilterActionAccordingToLocalUseCase(
            appPrefsRepository = appPrefsRepository,
            androidSystemPrefRepository = androidSystemPrefRepository
        )
    }

    private val filterActionAccordingToRemoteUseCase by lazy {
        FilterActionAccordingToRemoteUseCase()
    }

    private val sortActionsUseCase by lazy {
        SortActionsUseCase()
    }

    private val getActionsUseCase by lazy {
        GetActionUseCase(
            remoteActionsRepository = remoteActionsRepository,
            filterActionAccordingToLocal = filterActionAccordingToLocalUseCase,
            filterActionAccordingToRemoteUseCase = filterActionAccordingToRemoteUseCase,
            sortActionsUseCase = sortActionsUseCase,
        )
    }

    private val trackActionUseCase by lazy {
        TrackActionUseCase(
            appPrefsRepository = appPrefsRepository,
        )
    }


    lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context

    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ActionsViewModel::class.java -> ActionsViewModel(
                getActionsUseCase,
                ioDispatcher = ioDispacher,
                trackAction = trackActionUseCase,
            )
            else -> throw IllegalArgumentException("Cannot create factory for ${modelClass.simpleName}")
        } as T
    }
}