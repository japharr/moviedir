package com.jeliliadesina.moviedir.util.ui

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(): ViewModel()  {
    private var mNavigator: WeakReference<N>? = null

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }

    fun getNavigator(): N? {
        return mNavigator?.get()
    }
}