package com.jeliliadesina.moviedir.api

import com.google.gson.annotations.SerializedName

data class ResultsResponse<T> (
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long,
    @SerializedName("results")
    val results: List<T>
)