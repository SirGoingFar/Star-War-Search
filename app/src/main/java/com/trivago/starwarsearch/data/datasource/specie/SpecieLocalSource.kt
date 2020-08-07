package com.trivago.starwarsearch.data.datasource.specie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie
import com.trivago.starwarsearch.data.repository.character.CharacterRepository
import javax.inject.Inject

class SpecieLocalSource @Inject constructor(
    private val characterRepository: CharacterRepository
) : SpecieDataSource {

    private val specieHashMap: HashMap<String, Specie> = HashMap()

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return characterRepository.fetchSpecieListByCharacterUrl(url)
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