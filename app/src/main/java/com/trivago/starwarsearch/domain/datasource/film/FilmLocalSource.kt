package com.trivago.starwarsearch.domain.datasource.film

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.film.Film
import com.trivago.starwarsearch.domain.repository.character.CharacterRepository
import javax.inject.Inject

class FilmLocalSource @Inject constructor(
    private val characterRepository: CharacterRepository
) : FilmDataSource {

    private val filmHashMap: HashMap<String, Film> = HashMap()

    override suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return characterRepository.fetchFilmListByCharacterUrl(url)
    }

    override suspend fun fetchFilmDetailByFilmUrl(url: String): Either<Failure, Film> {
        return if (filmHashMap.containsKey(url))
            Either.Right(filmHashMap[url]!!)
        else Either.Left(
            ItemNotFoundException
        )
    }

    override suspend fun save(url: String, film: Film) {
        filmHashMap[url] = film
    }

}