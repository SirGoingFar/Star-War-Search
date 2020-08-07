package com.trivago.starwarsearch.data.dto.film

import com.google.gson.annotations.SerializedName

data class Movie(
    val title: String,
    @SerializedName("opening_crawl")
    val openingCrawl: String
)