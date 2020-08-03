package com.trivago.starwarsearch.domain.datasource.film

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.film.Film

interface FilmDataSource {

    suspend fun fetchFilmListByCharacterUrl(url:String): Either<Failure, List<String>>

    suspend fun fetchFilmDetailByFilmUrl(url: String): Either<Failure, Film>

    suspend fun save(url: String, film: Film)

}