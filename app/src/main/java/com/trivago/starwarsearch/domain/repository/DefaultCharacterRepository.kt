package com.trivago.starwarsearch.domain.repository

import com.trivago.starwarsearch.core.exception.EndOfListException
import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.exception.NetworkFailure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.di.annotation.CharacterLocalDataSourceQualifier
import com.trivago.starwarsearch.di.annotation.CharacterRemoteDataSourceQualifier
import com.trivago.starwarsearch.domain.datasource.CharacterDataSource
import com.trivago.starwarsearch.domain.dto.Character
import javax.inject.Inject

class DefaultCharacterRepository @Inject constructor(
    @CharacterLocalDataSourceQualifier
    private val localDataSource: CharacterDataSource,
    @CharacterRemoteDataSourceQualifier
    private val remoteDataSource: CharacterDataSource
) : CharacterRepository {

    private var mLastSearchTerm: String? = null
    private var mCurrentPage: Int = 0
    private var isLastPage: Boolean = false

    override suspend fun searchCharacterWithTerm(term: String): Either<Failure, List<Character>> {

        //If the last page has been fetched, throw an end of list failure
        if (!hasSearchTermChanged(term) && isLastPage)
            return Either.Left(EndOfListException)

        //search term has changed
        val hasSearchTermChanged = hasSearchTermChanged(term)

        //set the value of search term
        mLastSearchTerm = term

        if (hasSearchTermChanged) {
            //reset page number to fetch
            mCurrentPage = 0
        }

        val response = remoteDataSource.searchCharacter(term, ++mCurrentPage)

        return if (response.isLeft) {
            if ((response as Either.Left).a is NetworkFailure.ApiCall &&
                (response.a as NetworkFailure.ApiCall).errorCode == 404
            ) {
                if (hasSearchTermChanged)
                    localDataSource.deleteAll()
            }

            //API call failed, decrement the page tracker
            --mCurrentPage

            isLastPage = false

            response
        } else {

            val resp = (response as Either.Right).b
            val characterList = resp.list
            isLastPage = resp.next.isNullOrEmpty()

            //clear the cached list because the search term changed
            if (hasSearchTermChanged)
                localDataSource.deleteAll()

            //save the new list in cache
            localDataSource.save(characterList)

            Either.Right(((localDataSource.fetchCachedCharacterList()) as Either.Right).b)
        }
    }

    override suspend fun fetchCharacterByUrl(cardId: String): Either<Failure, Character> {
        return localDataSource.fetchCharacterByUrl(cardId)
    }

    override suspend fun fetchCachedCharacterList(): Either<Failure, List<Character>> {
        return localDataSource.fetchCachedCharacterList()
    }

    private fun hasSearchTermChanged(searchTerm: String) =
        (mLastSearchTerm == null && searchTerm.isNotEmpty()) || mLastSearchTerm != searchTerm

}