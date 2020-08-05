package com.trivago.starwarsearch.domain.repository.specie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.datasource.specie.SpecieDataSource
import com.trivago.starwarsearch.domain.dto.specie.Planet
import com.trivago.starwarsearch.domain.dto.specie.Specie

class FakeSpecieRepository(
    private val localDataSource: SpecieDataSource,
    private val remoteDataSource: SpecieDataSource
) : SpecieRepository {

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return localDataSource.fetchSpecieListByCharacterUrl(url)
    }

    override suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie> {
        return remoteDataSource.fetchSpecieDetailBySpecieUrl(url)
    }

    override suspend fun fetchPlanetDetailByUrl(
        specieUrl: String,
        homeWorldUrl: String
    ): Either<Failure, Planet> {
        return remoteDataSource.fetchSpeciePlanetByUrl(homeWorldUrl)
    }

}