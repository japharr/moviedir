package com.jeliliadesina.moviedir

import android.app.Application
import com.jeliliadesina.moviedir.util.CrashReportingTree
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())

        InternetAvailabilityChecker.init(this)
    }
}