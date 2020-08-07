package com.trivago.starwarsearch.data.datasource.specie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie
import com.trivago.starwarsearch.data.network.DefaultAppNetworkService
import com.trivago.starwarsearch.common.network.source.BaseRemoteSource
import javax.inject.Inject

class SpecieRemoteSource @Inject constructor(
    private val networkService: DefaultAppNetworkService
) : BaseRemoteSource(), SpecieDataSource {

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not necessary for remote data source")
    }

    override suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie> {
        return request { networkService.getSpecieApi().fetchSpecieDetail(url) }
    }

    override suspend fun fetchSpeciePlanetByUrl(url: String): Either<Failure, Planet> {
        return request { networkService.getPlanetApi().fetchPlanetDetail(url) }
    }

    override suspend fun save(url: String, specie: Specie) {
        TODO("Not necessary for remote data source")
    }

}