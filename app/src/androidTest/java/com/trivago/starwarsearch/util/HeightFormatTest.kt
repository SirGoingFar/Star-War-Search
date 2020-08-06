package com.trivago.starwarsearch.util

import com.trivago.starwarsearch.views.util.formatHeight
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class HeightFormatTest {

    @Test
    fun formatNonNumberHeightString_returnInputString(){
        val nonNumberHeightString = "unknown"
        val formattedHeightString = formatHeight(nonNumberHeightString)
        assertThat(formattedHeightString, `is`(nonNumberHeightString))
    }

    @Test
    fun formatNumberHeightString_returnFormattedHeightString(){
        val numberHeightString = "33.4"
        val formattedHeightString = formatHeight(numberHeightString)
        val expectedHeightString = "33.4cm, 13.15\""
        assertThat(formattedHeightString, `is`(expectedHeightString))
    }

}