package com.jeliliadesina.moviedir.util.ui

import java.text.DecimalFormat

val formatter: DecimalFormat = DecimalFormat("$#,###")

fun Long.toMoneyValue(): String? = formatter.format(this)

fun Int?.toDuration(): String {
  val duration = this ?: 0 * 60 * 60
  val hours: Int = duration / 60
  val minutes: Int = duration % 60

  return String.format("%02dh:%02dm", hours, minutes)
}