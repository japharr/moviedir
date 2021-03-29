package com.jeliliadesina.moviedir.movie.data

import android.text.TextUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jeliliadesina.moviedir.Constants.BASE_BACKDROP_IMAGE_URL
import com.jeliliadesina.moviedir.Constants.BASE_POSTER_IMAGE_URL
import com.jeliliadesina.moviedir.data.NameField
import com.jeliliadesina.moviedir.util.ui.asDateOnly
import com.jeliliadesina.moviedir.util.ui.asServerDate
import com.jeliliadesina.moviedir.util.ui.asYear
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
    var runtime: Int? = 0,
    var revenue: Long? = 0L,
    var budget: Long? = 0L,
    var adult: Boolean? = false,
    var genres: List<NameField?>? = emptyList(),
    @field:SerializedName("vote_count")
    var voteCount: Long = 0L,
    @field:SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @field:SerializedName("release_date")
    var releaseDate: String? = null,
    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @field:SerializedName("production_companies")
    var productionCompanies: List<NameField?>? = emptyList(),
    @field:SerializedName("production_countries")
    var productionCountries: List<NameField?>? = emptyList(),
    @field:SerializedName("spoken_languages")
    var spokenLanguages: List<NameField?>? = emptyList()
) {
    val imageUrl: String get() = "$BASE_POSTER_IMAGE_URL$posterPath"

    val backdropPathUrl: String get() = "$BASE_BACKDROP_IMAGE_URL$backdropPath"

    val releasedDate: Date? get() = releaseDate?.asServerDate()

    val releasedYear: String? get() = releaseDate?.asServerDate()?.asYear()

    val ratings: String get() = String.format("%1$.1f", voteAverage, Locale.ENGLISH)

    val allGenres: String? get() = genres?.joinToString()

    val allProductionCompanies: String? get() = productionCompanies?.joinToString()

    val allProductionCountries: String? get() = productionCountries?.joinToString()

    val allSpokenLanguages: String? get() = spokenLanguages?.joinToString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (title != other.title) return false
        if (originalTitle != other.originalTitle) return false
        if (originalLanguage != other.originalLanguage) return false
        if (imdbId != other.imdbId) return false
        if (posterPath != other.posterPath) return false
        if (homepage != other.homepage) return false
        if (overview != other.overview) return false
        if (popularity != other.popularity) return false
        if (status != other.status) return false
        if (tagline != other.tagline) return false
        if (runtime != other.runtime) return false
        if (revenue != other.revenue) return false
        if (budget != other.budget) return false
        if (adult != other.adult) return false
        if (voteCount != other.voteCount) return false
        if (voteAverage != other.voteAverage) return false
        if (releaseDate != other.releaseDate) return false
        if (backdropPath != other.backdropPath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (imdbId?.hashCode() ?: 0)
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (homepage?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + popularity.hashCode()
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (tagline?.hashCode() ?: 0)
        result = 31 * result + (runtime ?: 0)
        result = 31 * result + (revenue?.hashCode() ?: 0)
        result = 31 * result + (budget?.hashCode() ?: 0)
        result = 31 * result + (adult?.hashCode() ?: 0)
        result = 31 * result + voteCount.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)

        return result
    }
}