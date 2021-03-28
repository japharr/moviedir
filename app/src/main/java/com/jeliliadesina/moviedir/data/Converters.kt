package com.jeliliadesina.moviedir.data

import androidx.room.TypeConverter

class Converters {
  @TypeConverter
  fun toNameField(input: String?): List<NameField> {
    return input?.split(",")
        ?.map { NameField(it.trim()) } ?: emptyList()
  }

  @TypeConverter
  fun fromNameField(status: List<NameField>?): String? {
    return status?.joinToString()
  }
}