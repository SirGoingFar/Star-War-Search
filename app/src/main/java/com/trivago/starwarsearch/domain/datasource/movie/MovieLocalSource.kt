package com.trivago.starwarsearch.domain.datasource.movie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.film.Movie
import com.trivago.starwarsearch.domain.repository.character.CharacterRepository
import javax.inject.Inject

class MovieLocalSource @Inject constructor(
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