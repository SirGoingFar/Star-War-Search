package com.trivago.starwarsearch.domain.datasource.movie

import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.domain.datasource.character.FakeCharacterLocalSource
import com.trivago.starwarsearch.domain.datasource.character.FakeCharacterRemoteSource
import com.trivago.starwarsearch.domain.datasource.specie.data.CharacterDataUtil
import com.trivago.starwarsearch.domain.dto.film.Movie
import com.trivago.starwarsearch.domain.repository.character.FakeCharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieLocalSourceTest {

    private lateinit var localSource: MovieLocalSource

    @Before
    fun setUp() = runBlockingTest {
        localSource = MovieLocalSource(
            FakeCharacterRepository(
                FakeCharacterLocalSource(),
                FakeCharacterRemoteSource(CharacterDataUtil.searchCharacterResponse)
            )
        )
    }

    @Test
    fun fetchMovieListByCharacterInvalidUrl_returnItemNotFound() = runBlockingTest {
        val invalidCharacterUrl = "invalid_character_url"
        localSource.fetchMovieListByCharacterUrl(invalidCharacterUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchMovieListByCharacterValidUrl_returnMovieList() = runBlockingTest {
        val character = CharacterDataUtil.characterList[0]
        val validCharacterUrl = character.url
        localSource.fetchMovieListByCharacterUrl(validCharacterUrl).fold({}, {
            assertThat(it, `is`(character.films))
        })
    }

    @Test
    fun fetchMovieDetailByMovieInvalidUrl_returnItemNotFound() = runBlockingTest {
        val invalidMovieUrl = "invalid_movie_url"
        localSource.fetchMovieDetailByMovieUrl(invalidMovieUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchMovieDetailByMovieNonExistentUrl_returnItemNotFound() = runBlockingTest {
        val nonExistentMovieUrl = "http://swapi.dev/api/films/1/"
        localSource.fetchMovieDetailByMovieUrl(nonExistentMovieUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchMovieDetailByMovieValidUrl_returnMovieDetail() = runBlockingTest {
        val validMovieUrl = "http://swapi.dev/api/films/1/"

        val movie = Movie("Title", "Opening Crawl")

        //save the movie first
        localSource.save(validMovieUrl, movie)

        localSource.fetchMovieDetailByMovieUrl(validMovieUrl).fold({}, {
            assertThat(it, `is`(movie))
        })
    }
}