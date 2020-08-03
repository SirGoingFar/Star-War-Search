package com.trivago.starwarsearch.di.module

import com.trivago.starwarsearch.di.annotation.character.CharacterLocalDataSourceQualifier
import com.trivago.starwarsearch.di.annotation.character.CharacterRemoteDataSourceQualifier
import com.trivago.starwarsearch.di.annotation.film.FilmLocalDataSourceQualifier
import com.trivago.starwarsearch.di.annotation.film.FilmRemoteDataSourceQualifier
import com.trivago.starwarsearch.domain.datasource.character.CharacterDataSource
import com.trivago.starwarsearch.domain.datasource.character.CharacterLocalSource
import com.trivago.starwarsearch.domain.datasource.character.CharacterRemoteSource
import com.trivago.starwarsearch.domain.datasource.film.FilmDataSource
import com.trivago.starwarsearch.domain.datasource.film.FilmLocalSource
import com.trivago.starwarsearch.domain.datasource.film.FilmRemoteSource
import dagger.Module
import dagger.Provides

@Module
object DataSourceModule {

    @CharacterLocalDataSourceQualifier
    @Provides
    fun provideCharacterLocalDataSource(localDataSource: CharacterLocalSource): CharacterDataSource =
        localDataSource


    @CharacterRemoteDataSourceQualifier
    @Provides
    fun provideCharacterRemoteDataSource(remoteDataSource: CharacterRemoteSource): CharacterDataSource =
        remoteDataSource

    @FilmLocalDataSourceQualifier
    @Provides
    fun provideFilmLocalDataSource(localSource: FilmLocalSource): FilmDataSource = localSource

    @FilmRemoteDataSourceQualifier
    @Provides
    fun provideFilmRemoteDataSource(remoteSource: FilmRemoteSource): FilmDataSource = remoteSource

}