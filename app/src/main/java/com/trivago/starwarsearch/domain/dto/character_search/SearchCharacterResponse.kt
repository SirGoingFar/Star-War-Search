package com.trivago.starwarsearch.domain.dto.character_search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.trivago.starwarsearch.domain.dto.character_search.Character
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchCharacterResponse(
    val next: String?,
    val page: Int,
    @SerializedName("results")
    val list: List<Character>
) : Parcelable