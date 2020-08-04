package com.trivago.starwarsearch.domain.usecase.species

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.domain.dto.specie.Specie
import com.trivago.starwarsearch.domain.repository.specie.SpecieRepository
import javax.inject.Inject

class FetchSpecieDetailByUrl @Inject constructor(
    private val specieRepository: SpecieRepository
) : UseCase<Specie, String>() {
    override suspend fun execute(params: String): Either<Failure, Specie> {
        return specieRepository.fetchSpecieDetailBySpecieUrl(params)
    }
}