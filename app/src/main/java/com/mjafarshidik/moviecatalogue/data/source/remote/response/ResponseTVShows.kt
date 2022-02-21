package com.mjafarshidik.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseTVShows(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val resultsTV: List<ResultsItemTV>,
)

data class ResultsItemTV(
    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("original_name")
    val originalName: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("genres")
    val genres: List<GenresItemTV>,
)

data class GenresItemTV(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)