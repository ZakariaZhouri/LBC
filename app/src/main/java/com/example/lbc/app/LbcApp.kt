package com.example.lbc.app

import android.app.Application
import com.example.lbc.app.di.apiModule
import com.example.lbc.app.di.netModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class LbcApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LbcApp)
            modules(listOf(apiModule, netModule))
        }
    }
}