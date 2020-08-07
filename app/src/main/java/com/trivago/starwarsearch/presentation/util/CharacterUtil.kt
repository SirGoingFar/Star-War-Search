package com.trivago.starwarsearch.presentation.util

import android.text.SpannableStringBuilder

fun formatHeight(height: String): String {
    return try {
        val ssb = SpannableStringBuilder()
        ssb.append(height.plus("cm, "))
        val heightDouble = height.toDouble()
        val heightInInches = heightDouble / 2.54
        ssb.append(String.format("%.2f\"", heightInInches))
        ssb.toString()
    } catch (ex: Exception) {
        height
    }
}