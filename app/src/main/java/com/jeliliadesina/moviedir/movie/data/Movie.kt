package com.jeliliadesina.moviedir.movie.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jeliliadesina.moviedir.Constants.BASE_POSTER_IMAGE_URL

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    var id: Int = 0,
    var title: String,
    @field:SerializedName("original_title")
    var originalTitle: String,
    @field:SerializedName("original_language")
    var originalLanguage: String?,
    @field:SerializedName("imdb_id")
    var imdbId: String?,
    @field:SerializedName("poster_path")
    var posterPath: String?,
    var homepage: String?,
    var overview: String?,
    var popularity: Double = 0.0,
    var status: String?,
    var tagline: String?,
    var voteCount: Long = 0L,
    @field:SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @field:SerializedName("release_date")
    var releaseDate: String?
) {
    val imageUrl: String get() = "$BASE_POSTER_IMAGE_URL$posterPath"
}