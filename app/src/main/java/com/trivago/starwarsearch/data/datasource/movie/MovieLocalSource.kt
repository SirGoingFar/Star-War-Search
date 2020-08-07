package com.trivago.starwarsearch.data.datasource.movie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.film.Movie
import com.trivago.starwarsearch.data.repository.character.CharacterRepository
import javax.inject.Inject

open class MovieLocalSource @Inject constructor(
    private val characterRepository: CharacterRepository
) : MovieDataSource {

    private val movieHashMap: HashMap<String, Movie> = HashMap()

    override suspend fun fetchMovieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return characterRepository.fetchFilmListByCharacterUrl(url)
    }

    override suspend fun fetchMovieDetailByMovieUrl(url: String): Either<Failure, Movie> {
        return if (movieHashMap.containsKey(url))
            Either.Right(movieHashMap[url]!!)
        else Either.Left(
            ItemNotFoundException
        )
    }

    override suspend fun save(url: String, film: Movie) {
        movieHashMap[url] = film
    }

}