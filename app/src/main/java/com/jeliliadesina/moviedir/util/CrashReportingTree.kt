package com.jeliliadesina.moviedir.util

import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) return

        //FakeCrashLibrary.log(priority, tag, message)
        //FirebaseCrashlytics.getInstance().log(message)

        if (t != null) {
            // Firebase Crash Reporting
            //FirebaseCrashlytics.getInstance().recordException(t)
        }
    }
}