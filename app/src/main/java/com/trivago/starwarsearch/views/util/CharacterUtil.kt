package com.trivago.starwarsearch.views.util

import android.text.SpannableStringBuilder

fun formatHeight(height: String): String {
    val ssb = SpannableStringBuilder()
    ssb.append(height.plus("cm, "))
    val heightDouble = height.toDouble()
    val heightInInches = heightDouble / 2.54
    ssb.append(String.format("%.2f\"", heightInInches))
    return ssb.toString()
}