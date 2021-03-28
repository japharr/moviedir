package com.jeliliadesina.moviedir.data

data class NameField(
    var name: String? = null
) {
  override fun toString(): String {
    return name ?: ""
  }
}