package com.trivago.starwarsearch.domain.repository.movie

import com.google.common.base.CharMatcher.isNot
import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.datasource.movie.FakeMovieLocalSource
import com.trivago.starwarsearch.domain.datasource.movie.FakeMovieRemoteSource
import com.trivago.starwarsearch.domain.datasource.specie.data.CharacterDataUtil
import com.trivago.starwarsearch.domain.dto.film.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultMovieRepositoryTest {

    private lateinit var movie: Movie
    private lateinit var movieUrl: String
    private lateinit var repository: DefaultMovieRepository

    @Before
    fun setUp() {
        movie = Movie("Title", "Opening Crawl")
        movieUrl = "http://swapi.dev/api/films/1/"
        repository = DefaultMovieRepository(
            FakeMovieLocalSource(),
            FakeMovieRemoteSource(movie)
        )
    }

    @Test
    fun fetchMovieListByInvalidCharacterUrl_returnItemNotFoundException() = runBlockingTest {
        val invalidUrl = "invalid_character_url"
        repository.fetchMovieListByCharacterUrl(invalidUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchMovieListByNonExistentCharacterUrl_returnItemNotFoundException() = runBlockingTest {
        val nonExistentUrl = "non_existent_character_url"

        repository.fetchMovieListByCharacterUrl(nonExistentUrl).fold({
            assertThat(it is ItemNotFoundException, `is`(true))
        }, {})
    }

    @Test
    fun fetchMovieListByValidCharacterUrl_returnMovieList() = runBlockingTest {

        val character = CharacterDataUtil.characterList[0]

        repository.fetchMovieListByCharacterUrl(character.url).fold({}, {
            assertThat(it, `is`(character.films))
        })
    }

    @Test
    fun fetchMovieByMovieUrl_returnMovie() = runBlockingTest {

        val retrievedMovie = (repository.fetchMovieDetailByMovieUrl(movieUrl) as Either.Right).b

        assertThat(retrievedMovie, `is`(movie))
        assertThat((retrievedMovie == Movie("Me", "You")), `is`(false))
    }

}