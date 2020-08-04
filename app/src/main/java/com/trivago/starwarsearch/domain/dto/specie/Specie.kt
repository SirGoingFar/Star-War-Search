package com.trivago.starwarsearch.domain.dto.specie

import com.google.gson.annotations.SerializedName

data class Specie(
    val name: String,
    val language: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    var homeWorldName:String?,
    var population: String?
)