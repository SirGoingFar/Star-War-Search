package com.trivago.starwarsearch.data.repository.specie

import com.trivago.starwarsearch.common.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.data.datasource.specie.FakeSpecieLocalSource
import com.trivago.starwarsearch.data.datasource.specie.FakeSpecieRemoteSource
import com.trivago.starwarsearch.data.datasource.specie.data.CharacterDataUtil
import com.trivago.starwarsearch.data.datasource.specie.data.SpecieDataUtil
import com.trivago.starwarsearch.data.datasource.specie.data.SpecieDataUtil.planetList
import com.trivago.starwarsearch.data.datasource.specie.data.SpecieDataUtil.specieList
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SpecieRepositoryTest {

    private lateinit var repository: FakeSpecieRepository
    private lateinit var specieUrlList: List<String>
    private lateinit var specieUrlToSpecieMap: Map<String, Specie>
    private lateinit var specieUrlToPlanetMap: Map<String, Planet>

    @Before
    fun setUp() {
        specieUrlList = SpecieDataUtil.specieUrlList
        specieUrlToSpecieMap = SpecieDataUtil.specieUrlToSpecieMap
        specieUrlToPlanetMap = SpecieDataUtil.specieUrlToPlanetMap
        repository = FakeSpecieRepository(
            FakeSpecieLocalSource(specieUrlList),
            FakeSpecieRemoteSource(
                specieUrlToSpecieMap,
                specieUrlToPlanetMap
            )
        )
    }

    @Test
    fun fetchSpecieListByInvalidCharacterUrl_returnsItemNotFoundException() = runBlockingTest {
        val invalidCharacterUrl = "invalid_character_url"
        repository.fetchSpecieListByCharacterUrl(invalidCharacterUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchSpecieListByValidCharacterUrl_returnsSpecieUrlList() = runBlockingTest {
        val invalidCharacterUrl = CharacterDataUtil.characterUrl[0]
        repository.fetchSpecieListByCharacterUrl(invalidCharacterUrl).fold({}, {
            assertThat(it, `is`(specieUrlList))
        })
    }

    @Test
    fun fetchSpecieDetailByInvalidSpecieUrl_returnsItemNotFoundException() = runBlockingTest {
        val specieUrl = "invalid_specie_url"
        repository.fetchSpecieDetailBySpecieUrl(specieUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchSpecieDetailByValidSpecieUrl_returnsSpecieDetail() = runBlockingTest {
        val specieUrl = specieUrlList[0]
        val specie = specieList[0]
        repository.fetchSpecieDetailBySpecieUrl(specieUrl).fold({}, {
            assertThat(it, `is`(specie))
        })
    }

    @Test
    fun fetchPlanetDetailByInvalidHomeWorldUrl_returnsItemNotFoundException() = runBlockingTest {
        val specieUrl = "invalid_specie_url"
        val homeWorldUrl = "invalid_homeworld_url"
        repository.fetchPlanetDetailByUrl(specieUrl, homeWorldUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchPlanetDetailByValidHomeWorldUrl_returnsPlanetDetail() = runBlockingTest {
        val planet = planetList[0]
        val specieUrl = specieUrlList[0]
        val homeWorldUrl = specieList[0].homeWorld
        repository.fetchPlanetDetailByUrl(specieUrl, homeWorldUrl).fold({ }, {
            assertThat(it, `is`(planet))
        })
    }

}