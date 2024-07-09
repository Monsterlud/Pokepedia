package com.monsalud.pokepedia

import android.app.Application
import com.monsalud.pokepedia.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PokepediaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@PokepediaApplication)
            modules(appModule)
        }
    }
}