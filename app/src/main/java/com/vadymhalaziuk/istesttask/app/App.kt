package com.vadymhalaziuk.istesttask.app

import android.app.Application
import com.vadymhalaziuk.istesttask.di.GlobalFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalFactory.init(applicationContext)
    }
}