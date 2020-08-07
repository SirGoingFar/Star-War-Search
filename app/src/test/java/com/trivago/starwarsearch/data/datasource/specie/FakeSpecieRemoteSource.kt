package com.trivago.starwarsearch.data.datasource.specie

import androidx.annotation.VisibleForTesting
import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie

@VisibleForTesting
class FakeSpecieRemoteSource(
    private val specieUrlToSpecieMap: Map<String, Specie>,
    private val specieUrlToPlanetMap: Map<String, Planet>
) : SpecieDataSource {

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie> {
        return if (url.isEmpty())
            Either.Left(ItemNotFoundException)
        else {
            val specie = specieUrlToSpecieMap[url]
            if (specie == null)
                Either.Left(ItemNotFoundException)
            else
                Either.Right(specie)
        }
    }

    override suspend fun fetchSpeciePlanetByUrl(url: String): Either<Failure, Planet> {
        return if (url.isEmpty())
            Either.Left(ItemNotFoundException)
        else {
            val planet = specieUrlToPlanetMap[url]
            if (planet == null)
                Either.Left(ItemNotFoundException)
            else
                Either.Right(planet)
        }
    }

    override suspend fun save(url: String, specie: Specie) {
        TODO("Not yet implemented")
    }

}