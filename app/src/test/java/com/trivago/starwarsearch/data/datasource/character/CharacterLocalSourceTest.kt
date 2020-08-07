package com.trivago.starwarsearch.data.datasource.character

import com.trivago.starwarsearch.common.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.data.datasource.specie.data.CharacterDataUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterLocalSourceTest {

    private lateinit var localSource: CharacterLocalSource

    @Before
    fun setUp() {
        localSource = CharacterLocalSource()
    }

    @Test
    fun fetchCharacterByInvalidUrl_returnItemNotFoundException() = runBlockingTest {
        val invalidCharacterUrl = "invalid_character_url"
        localSource.fetchCharacterByUrl(invalidCharacterUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchCharacterByValidUrl_returnItemNotFoundException() = runBlockingTest {
        val character = CharacterDataUtil.characterList[0]
        val characterUrl = character.url

        //save the character
        localSource.save(listOf(character))

        localSource.fetchCharacterByUrl(characterUrl).fold({}, {
            assertThat(it, `is`(character))
        })
    }

    @Test
    fun fetchCharacterList_returnListOfCharacters() = runBlockingTest {
        localSource.fetchCharacterList().fold({}, {
            assertThat(it.isEmpty(), `is`(true))
        })

        val character = CharacterDataUtil.characterList[0]

        //save the character
        localSource.save(listOf(character))

        localSource.fetchCharacterList().fold({}, {
            assertThat(it, `is`(listOf(character)))
        })
    }

    @Test
    fun fetchFilmListBuyInvalidCharacterUrl_returnItemNotFound() = runBlockingTest {
        val invalidCharacterUrl = "invalid_character_url"
        localSource.fetchFilmListByCharacterUrl(invalidCharacterUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchFilmListBuyValidCharacterUrl_returnFilmUrlList() = runBlockingTest {
        val character = CharacterDataUtil.characterList[0]
        val validCharacterUrl = character.url

        localSource.save(listOf(character))

        localSource.fetchFilmListByCharacterUrl(validCharacterUrl).fold({
        }, {
            assertThat(it, `is`(character.films))
        })
    }

    @Test
    fun deleteAll_returnsEmptyList()= runBlockingTest {

        val character = CharacterDataUtil.characterList[0]
        val validCharacterUrl = character.url

        localSource.save(listOf(character))

        localSource.fetchFilmListByCharacterUrl(validCharacterUrl).fold({
        }, {
            assertThat(it.isNotEmpty(), `is`(true))
        })

        localSource.deleteAll()

        localSource.fetchFilmListByCharacterUrl(validCharacterUrl).fold({
        }, {
            assertThat(it.isEmpty(), `is`(true))
        })

    }

}