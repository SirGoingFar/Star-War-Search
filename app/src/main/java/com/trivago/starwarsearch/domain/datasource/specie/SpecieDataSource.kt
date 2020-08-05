package com.trivago.starwarsearch.domain.datasource.specie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.specie.Planet
import com.trivago.starwarsearch.domain.dto.specie.Specie

interface SpecieDataSource {

    suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie>

    suspend fun fetchSpeciePlanetByUrl(url: String): Either<Failure, Planet>

    suspend fun save(url: String, specie: Specie)

}