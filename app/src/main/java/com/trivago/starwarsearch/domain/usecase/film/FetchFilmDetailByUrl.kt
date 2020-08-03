package com.trivago.starwarsearch.domain.usecase.film

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.domain.dto.film.Film
import com.trivago.starwarsearch.domain.repository.film.FilmRepository
import javax.inject.Inject

class FetchFilmDetailByUrl @Inject constructor(private val repository: FilmRepository) :
    UseCase<Film, String>() {
    override suspend fun execute(params: String): Either<Failure, Film> {
        return repository.fetchFilmDetailByFilmUrl(params)
    }
}