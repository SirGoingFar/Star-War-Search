package com.trivago.starwarsearch.domain.datasource.specie

import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.domain.datasource.specie.data.SpecieDataUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SpecieRemoteSourceTest {

    private lateinit var remoteSource: FakeSpecieRemoteSource

    @Before
    fun setUp() {
        remoteSource = FakeSpecieRemoteSource(
            SpecieDataUtil.specieUrlToSpecieMap,
            SpecieDataUtil.specieUrlToPlanetMap
        )
    }


    @Test
    fun fetchInvalidSpecieUrlForSpecie_returnItemNotFoundException() = runBlockingTest {
        val invalidSpecieUrl = "invalid_specie_url"
        remoteSource.fetchSpecieDetailBySpecieUrl(invalidSpecieUrl).fold(
            {
                assertThat(
                    it is ItemNotFoundException,
                    `is`(true)
                )
            },
            {}
        )
    }

    @Test
    fun fetchValidSpecieUrlForSpecie_returnSpecieDetail() = runBlockingTest {
        val validSpecieUrl = SpecieDataUtil.specieUrlList[0]
        remoteSource.fetchSpecieDetailBySpecieUrl(validSpecieUrl).fold({},
            {
                assertThat(it, `is`(SpecieDataUtil.specieList[0]))
            }
        )

    }

    @Test
    fun fetchInvalidSpecieUrlForPlanet_returnItemNotFoundException() = runBlockingTest {
        val invalidSpecieUrl = "invalid_specie_url"
        remoteSource.fetchSpecieDetailBySpecieUrl(invalidSpecieUrl).fold(
            {
                assertThat(
                    it is ItemNotFoundException,
                    `is`(true)
                )
            },
            {}
        )
    }

    @Test
    fun fetchValidSpecieUrlForPlanet_returnPlanetDetail() = runBlockingTest {
        val validSpecieUrl = SpecieDataUtil.specieUrlList[0]
        remoteSource.fetchSpeciePlanetByUrl(validSpecieUrl).fold({},
            {
                assertThat(it, `is`(SpecieDataUtil.specieUrlToPlanetMap[validSpecieUrl]))
            }
        )

    }

}