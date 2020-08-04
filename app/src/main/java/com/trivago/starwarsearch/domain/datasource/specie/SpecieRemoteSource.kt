package com.trivago.starwarsearch.domain.datasource.specie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.specie.Planet
import com.trivago.starwarsearch.domain.dto.specie.Specie
import com.trivago.starwarsearch.domain.network.AppNetworkService
import com.trivago.starwarsearch.network.source.BaseRemoteSource
import javax.inject.Inject

class SpecieRemoteSource @Inject constructor(
    private val networkService: AppNetworkService
) : BaseRemoteSource(), SpecieDataSource {

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie> {
        return request { networkService.specieApi.fetchSpecieDetail(url) }
    }

    override suspend fun fetchSpeciePlanetByUrl(url: String): Either<Failure, Planet> {
        return request { networkService.planetApi.fetchPlanetDetail(url) }
    }

    override suspend fun save(url: String, specie: Specie) {
        TODO("Not yet implemented")
    }

    override suspend fun findSpecieByUrl(url: String): Either<Failure, Specie> {
        TODO("Not yet implemented")
    }

}