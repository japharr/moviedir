package com.jeliliadesina.moviedir

import android.app.Application
import com.jeliliadesina.moviedir.di.appModule
import com.jeliliadesina.moviedir.di.dataModule
import com.jeliliadesina.moviedir.di.retrofitModule
import com.jeliliadesina.moviedir.di.viewModelModule
import com.jeliliadesina.moviedir.util.CrashReportingTree
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@KoinApiExtension
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())

        InternetAvailabilityChecker.init(this)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule, dataModule, retrofitModule))
        }
    }
}