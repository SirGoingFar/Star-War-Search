package com.trivago.starwarsearch.data.repository.specie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.ItemNetworkFetchFailure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.common.di.annotation.specie.SpecieLocalDataSourceQualifier
import com.trivago.starwarsearch.common.di.annotation.specie.SpecieRemoteDataSourceQualifier
import com.trivago.starwarsearch.data.datasource.specie.SpecieDataSource
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie
import javax.inject.Inject

class DefaultSpecieRepository @Inject constructor(
    @SpecieLocalDataSourceQualifier private val localSource: SpecieDataSource,
    @SpecieRemoteDataSourceQualifier private val remoteSource: SpecieDataSource
) : SpecieRepository {

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return localSource.fetchSpecieListByCharacterUrl(url)
    }

    override suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie> {

        //check cache if the specie detail is available
        val cachedSpecieDetailResp = localSource.fetchSpecieDetailBySpecieUrl(url)
        if (cachedSpecieDetailResp.isRight)
            return cachedSpecieDetailResp

        //Fetch Specie Detail
        val specieResp = remoteSource.fetchSpecieDetailBySpecieUrl(url)

        //If failed, return failure
        if (specieResp.isLeft)
            return Either.Left(ItemNetworkFetchFailure)

        var specie = (specieResp as Either.Right).b

        //It's successful, fetch the Home World data of the specie
        val planetResp = fetchPlanetDetailByUrl(url, specie.homeWorld)

        //If failed, return the specie detail
        if (planetResp.isLeft) {
            //save specie in a light cache
            localSource.save(url, specie)
            return specieResp
        }

        val planet = (planetResp as Either.Right).b

        specie = specie.copy(population = planet.population, homeWorldName = planet.name)

        //save updated specie in a light cache
        localSource.save(url, specie)

        return Either.Right(specie)
    }

    override suspend fun fetchPlanetDetailByUrl(
        specieUrl: String,
        homeWorldUrl: String
    ): Either<Failure, Planet> {
        val resp = remoteSource.fetchSpeciePlanetByUrl(homeWorldUrl)
        return if (resp is Either.Left)
            resp
        else {
            val planet = (resp as Either.Right).b

            val specieResp = localSource.fetchSpecieDetailBySpecieUrl(specieUrl)

            if (specieResp.isRight) {
                var specie = (specieResp as Either.Right).b
                specie = specie.copy(homeWorldName = planet.name, population = planet.population)
                //overwrite specie in light cache
                localSource.save(specieUrl, specie)
            }

            resp
        }
    }

}