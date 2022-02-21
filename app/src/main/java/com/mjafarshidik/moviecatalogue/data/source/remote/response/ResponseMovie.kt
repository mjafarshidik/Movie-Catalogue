package com.mjafarshidik.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseMovie(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<ResultsItem>
)

data class ResultsItem(
    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("genres")
    val genres: List<GenresItemMovie>,
)

data class GenresItemMovie(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)