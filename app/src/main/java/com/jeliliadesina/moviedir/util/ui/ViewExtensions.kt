package com.jeliliadesina.moviedir.util.ui

import android.view.View
import android.widget.ProgressBar
import java.text.DecimalFormat

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}