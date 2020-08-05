package com.trivago.starwarsearch.domain.repository.character

import com.trivago.starwarsearch.domain.datasource.character.FakeCharacterLocalSource
import com.trivago.starwarsearch.domain.datasource.character.FakeCharacterRemoteSource
import com.trivago.starwarsearch.domain.datasource.specie.data.CharacterDataUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterRepositoryTest {

    private lateinit var repository: DefaultCharacterRepository

    @Before
    fun setUp() = runBlockingTest {
        repository = DefaultCharacterRepository(
            FakeCharacterLocalSource(),
            FakeCharacterRemoteSource(CharacterDataUtil.searchCharacterResponse)
        )

        //the search process saves to the cache - let's do it first
        repository.searchCharacterWithTerm("term")
    }

    @Test
    fun searchCharacterWithTerm_returnCharacterList() = runBlockingTest {
        repository.searchCharacterWithTerm("Archilles").fold({}, {
            assertThat(it, `is`(CharacterDataUtil.characterList))
        })
    }

    @Test
    fun fetchCharacterByUrl_returnCharacter() = runBlockingTest {
        val character = CharacterDataUtil.characterList[0]
        repository.fetchCharacterByUrl(character.url).fold({}, {
            assertThat(it, `is`(character))
        })
    }

    @Test
    fun fetchCharacterList_returnListOfCharacter() = runBlockingTest {
        repository.fetchCharacterList().fold({}, {
            assertThat(it, `is`(CharacterDataUtil.characterList))
        })
    }

    @Test
    fun fetchFilmListByCharacterUrl_returnListOfFilmUrl() = runBlockingTest {
        val character = CharacterDataUtil.characterList[0]
        repository.fetchFilmListByCharacterUrl(character.url).fold({}, {
            assertThat(it, `is`(character.films))
        })
    }

    @Test
    fun fetchSpecieListByCharacterUrl_returnListOfSpecie() = runBlockingTest {
        val character = CharacterDataUtil.characterList[0]
        repository.fetchSpecieListByCharacterUrl(character.url).fold({}, {
            assertThat(it, `is`(character.species))
        })
    }

}