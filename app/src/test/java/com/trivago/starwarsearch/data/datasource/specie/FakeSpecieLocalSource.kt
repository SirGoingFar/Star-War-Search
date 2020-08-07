package com.trivago.starwarsearch.data.datasource.specie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie

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