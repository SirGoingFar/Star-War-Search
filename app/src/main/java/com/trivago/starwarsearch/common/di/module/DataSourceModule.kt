package com.trivago.starwarsearch.common.di.module

import com.trivago.starwarsearch.common.di.annotation.character.CharacterLocalDataSourceQualifier
import com.trivago.starwarsearch.common.di.annotation.character.CharacterRemoteDataSourceQualifier
import com.trivago.starwarsearch.common.di.annotation.movie.MovieLocalDataSourceQualifier
import com.trivago.starwarsearch.common.di.annotation.movie.MovieRemoteDataSourceQualifier
import com.trivago.starwarsearch.common.di.annotation.specie.SpecieLocalDataSourceQualifier
import com.trivago.starwarsearch.common.di.annotation.specie.SpecieRemoteDataSourceQualifier
import com.trivago.starwarsearch.data.datasource.character.CharacterDataSource
import com.trivago.starwarsearch.data.datasource.character.CharacterLocalSource
import com.trivago.starwarsearch.data.datasource.character.CharacterRemoteSource
import com.trivago.starwarsearch.data.datasource.movie.MovieDataSource
import com.trivago.starwarsearch.data.datasource.movie.MovieLocalSource
import com.trivago.starwarsearch.data.datasource.movie.MovieRemoteSource
import com.trivago.starwarsearch.data.datasource.specie.SpecieDataSource
import com.trivago.starwarsearch.data.datasource.specie.SpecieLocalSource
import com.trivago.starwarsearch.data.datasource.specie.SpecieRemoteSource
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

    @MovieLocalDataSourceQualifier
    @Provides
    fun provideMovieLocalDataSource(localSource: MovieLocalSource): MovieDataSource = localSource

    @MovieRemoteDataSourceQualifier
    @Provides
    fun provideMovieRemoteDataSource(remoteSource: MovieRemoteSource): MovieDataSource = remoteSource

    @SpecieLocalDataSourceQualifier
    @Provides
    fun provideSpecieLocalDataSource(localSource: SpecieLocalSource): SpecieDataSource = localSource

    @SpecieRemoteDataSourceQualifier
    @Provides
    fun provideSpecieRemoteDataSource(remoteSource: SpecieRemoteSource): SpecieDataSource =
        remoteSource

}