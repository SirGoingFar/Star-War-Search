package com.trivago.starwarsearch.domain.repository.specie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.specie.Planet
import com.trivago.starwarsearch.domain.dto.specie.Specie

interface SpecieRepository {

    suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie>

    suspend fun fetchPlanetDetailByUrl(
        specieUrl: String,
        homeWorldUrl: String
    ): Either<Failure, Planet>

}