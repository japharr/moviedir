package com.jeliliadesina.moviedir.movie.data

import android.text.TextUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jeliliadesina.moviedir.Constants.BASE_POSTER_IMAGE_URL
import com.jeliliadesina.moviedir.util.ui.asDateOnly
import java.util.*

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
    var imdbId: String? = null,
    @field:SerializedName("poster_path")
    var posterPath: String? = null,
    var homepage: String? = null,
    var overview: String? = null,
    var popularity: Double = 0.0,
    var status: String? = null,
    var tagline: String? = null,
    @field:SerializedName("vote_count")
    var voteCount: Long = 0L,
    @field:SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @field:SerializedName("release_date")
    var releaseDate: String? = null,
    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null
) {
    val imageUrl: String get() = "$BASE_POSTER_IMAGE_URL$posterPath"

    val backdropPathUrl: String get() = "$BASE_POSTER_IMAGE_URL$backdropPath"

    val releasedDateDate: Date? get() = releaseDate?.asDateOnly()

    val genres: String? get() = TextUtils.join(", ", arrayOf("Action"))
}