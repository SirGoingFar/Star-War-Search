package com.trivago.starwarsearch.di.module

import com.trivago.starwarsearch.di.annotation.CharacterLocalDataSourceQualifier
import com.trivago.starwarsearch.di.annotation.CharacterRemoteDataSourceQualifier
import com.trivago.starwarsearch.domain.datasource.CharacterDataSource
import com.trivago.starwarsearch.domain.datasource.CharacterLocalSource
import com.trivago.starwarsearch.domain.datasource.CharacterRemoteSource
import dagger.Module
import dagger.Provides

@Module
object DataSourceModule {

    @CharacterLocalDataSourceQualifier
    @Provides
    fun provideCardLocalDataSource(localDataSource: CharacterLocalSource): CharacterDataSource =
        localDataSource


    @CharacterRemoteDataSourceQualifier
    @Provides
    fun provideCardRemoteDataSource(remoteDataSource: CharacterRemoteSource): CharacterDataSource =
        remoteDataSource

}