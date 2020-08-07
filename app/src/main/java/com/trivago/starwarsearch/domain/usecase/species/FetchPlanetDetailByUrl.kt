package com.trivago.starwarsearch.domain.usecase.species

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.common.core.interactor.UseCase
import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.repository.specie.SpecieRepository
import javax.inject.Inject

class FetchPlanetDetailByUrl @Inject constructor(private val repository: SpecieRepository) :
    UseCase<Planet, List<String>>() {
    override suspend fun execute(params: List<String>): Either<Failure, Planet> {
        return repository.fetchPlanetDetailByUrl(params[0], params[1])
    }
}