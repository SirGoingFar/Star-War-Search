package com.trivago.starwarsearch.domain.usecase.species

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.common.core.interactor.UseCase
import com.trivago.starwarsearch.data.dto.specie.Specie
import com.trivago.starwarsearch.data.repository.specie.SpecieRepository
import javax.inject.Inject

class FetchSpecieDetailByUrl @Inject constructor(
    private val specieRepository: SpecieRepository
) : UseCase<Specie, String>() {
    override suspend fun execute(params: String): Either<Failure, Specie> {
        return specieRepository.fetchSpecieDetailBySpecieUrl(params)
    }
}