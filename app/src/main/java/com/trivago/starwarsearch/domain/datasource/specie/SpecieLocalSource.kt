package com.trivago.starwarsearch.domain.datasource.specie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.specie.Planet
import com.trivago.starwarsearch.domain.dto.specie.Specie
import com.trivago.starwarsearch.domain.repository.character.CharacterRepository
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

    override suspend fun findSpecieByUrl(url: String): Either<Failure, Specie> {
        return if (specieHashMap.containsKey(url))
            Either.Right(specieHashMap[url]!!)
        else
            Either.Left(ItemNotFoundException)
    }

    override suspend fun fetchSpeciePlanetByUrl(url: String): Either<Failure, Planet> {
        TODO("Not yet implemented")
    }

}