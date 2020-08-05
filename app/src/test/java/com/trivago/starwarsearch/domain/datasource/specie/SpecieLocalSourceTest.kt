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
class SpecieLocalSourceTest {

    private lateinit var localSource: FakeSpecieLocalSource

    @Before
    fun setup() = runBlockingTest {
        localSource = FakeSpecieLocalSource(SpecieDataUtil.specieUrlList)

        //seed the local cache
        SpecieDataUtil.specieList.forEachIndexed { index, specie ->
            localSource.save(SpecieDataUtil.specieUrlList[index], specie)
        }

    }

    @Test
    fun fetchSpecieUrlListWithEmptyCharacterUrl_returnItemNotFoundException() = runBlockingTest {
        val characterUrl = ""
        localSource.fetchSpecieListByCharacterUrl(characterUrl).fold(
            {
                assertThat(
                    it is ItemNotFoundException,
                    `is`(true)
                )
            }, {})
    }

    @Test
    fun fetchSpecieUrlListWithValidCharacterUrl_returnSpecieUrlList() = runBlockingTest {
        val characterUrl = "http://swapi.dev/api/people/1/"
        localSource.fetchSpecieListByCharacterUrl(characterUrl).fold({}, {
            assertThat(it, `is`(SpecieDataUtil.specieUrlList))
        })
    }

    @Test
    fun fetchSpecieDetailBySpecieUrl_returnItemNotFoundException() = runBlockingTest {
        val specieUrl = ""
        localSource.fetchSpecieDetailBySpecieUrl(specieUrl).fold(
            {
                assertThat(
                    it is ItemNotFoundException,
                    `is`(true)
                )
            }, {})
    }

    @Test
    fun fetchSpecieDetailBySpecieUrl_returnSpecieDetail() = runBlockingTest {
        val specieUrl = SpecieDataUtil.specieUrlList[0]
        localSource.fetchSpecieDetailBySpecieUrl(specieUrl).fold({}, {
            assertThat(it, `is`(SpecieDataUtil.specieList[0]))
        })
    }

    @Test
    fun saveAndQuerySpecieDetailBySpecieUrl_returnsSpecieDetail() = runBlockingTest {
        val savedSpecieUrl = SpecieDataUtil.specieUrlList[2]
        val unsavedSpecieUrl = SpecieDataUtil.specieUrlList[1]
        val specie = SpecieDataUtil.specieList[3]

        //save
        localSource.save(savedSpecieUrl, specie)

        //query - valid entry
        localSource.fetchSpecieDetailBySpecieUrl(savedSpecieUrl).fold({}, {
            assertThat(it, `is`(specie))
        })

        //query - invalid entry
        localSource.fetchSpecieDetailBySpecieUrl(unsavedSpecieUrl).fold({
            assertThat(
                it is ItemNotFoundException,
                `is`(true)
            )
        }, {})
    }


}