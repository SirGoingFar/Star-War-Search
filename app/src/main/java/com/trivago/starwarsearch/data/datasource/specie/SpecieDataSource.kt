package com.trivago.starwarsearch.data.datasource.specie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie

interface SpecieDataSource {

    suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie>

    suspend fun fetchSpeciePlanetByUrl(url: String): Either<Failure, Planet>

    suspend fun save(url: String, specie: Specie)

}