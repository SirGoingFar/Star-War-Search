package com.trivago.starwarsearch.domain.datasource.film

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.film.Film
import com.trivago.starwarsearch.domain.network.AppNetworkService
import com.trivago.starwarsearch.network.source.BaseRemoteSource
import javax.inject.Inject

class FilmRemoteSource @Inject constructor(
    private val networkService: AppNetworkService
) : BaseRemoteSource(), FilmDataSource {

    override suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFilmDetailByFilmUrl(url: String): Either<Failure, Film> {
        return request { networkService.filmApi.fetchFilmDetail(url) }
    }

    override suspend fun save(url: String, film: Film) {
        TODO("Not yet implemented")
    }
}