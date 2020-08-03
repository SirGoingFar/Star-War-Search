package com.trivago.starwarsearch.domain.dto.film

import com.google.gson.annotations.SerializedName

data class Film(
    val title: String,
    @SerializedName("opening_crawl")
    val openingCrawl: String
)