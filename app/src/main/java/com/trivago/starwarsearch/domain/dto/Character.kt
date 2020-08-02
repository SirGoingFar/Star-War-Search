package com.trivago.starwarsearch.domain.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(

    val name: String,

    val height: String,

    val gender: String,

    val url: String,

    val species: List<String>,

    val films: List<String>,

    @SerializedName("homeworld")
    val homeWorld: String,

    @SerializedName("birth_year")
    val birthYear: String

) : Parcelable