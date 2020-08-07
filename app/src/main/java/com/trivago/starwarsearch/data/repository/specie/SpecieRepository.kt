package com.trivago.starwarsearch.data.repository.specie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie

interface SpecieRepository {

    suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie>

    suspend fun fetchPlanetDetailByUrl(
        specieUrl: String,
        homeWorldUrl: String
    ): Either<Failure, Planet>

}