package com.trivago.starwarsearch.domain.datasource.specie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.specie.Planet
import com.trivago.starwarsearch.domain.dto.specie.Specie

class FakeSpecieLocalSource(
    private val specieUrlList: List<String>
) : SpecieDataSource {

    private val specieHashMap: HashMap<String, Specie> = HashMap()

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return if (url.isNotEmpty())
            Either.Right(specieUrlList)
        else
            Either.Left(ItemNotFoundException)
    }

    override suspend fun fetchSpecieDetailBySpecieUrl(url: String): Either<Failure, Specie> {
        return if (specieHashMap.containsKey(url))
            Either.Right(specieHashMap[url]!!)
        else Either.Left(
            ItemNotFoundException
        )
    }

    override suspend fun save(url: String, specie: Specie) {
        specieHashMap[url] = specie
    }

    override suspend fun fetchSpeciePlanetByUrl(url: String): Either<Failure, Planet> {
        TODO("Not necessary for local data source")
    }

}