package com.trivago.starwarsearch.common.di.module

import com.trivago.starwarsearch.common.di.annotation.SearchScope
import com.trivago.starwarsearch.data.repository.character.CharacterRepository
import com.trivago.starwarsearch.data.repository.character.DefaultCharacterRepository
import com.trivago.starwarsearch.data.repository.movie.DefaultMovieRepository
import com.trivago.starwarsearch.data.repository.movie.MovieRepository
import com.trivago.starwarsearch.data.repository.specie.DefaultSpecieRepository
import com.trivago.starwarsearch.data.repository.specie.SpecieRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @SearchScope
    @Provides
    fun provideCharacterRepository(defaultCharacterRepository: DefaultCharacterRepository): CharacterRepository =
        defaultCharacterRepository

    @SearchScope
    @Provides
    fun provideMovieRepository(defaultMovieRepository: DefaultMovieRepository): MovieRepository =
        defaultMovieRepository

    @SearchScope
    @Provides
    fun provideSpecieRepository(defaultSpecieRepository: DefaultSpecieRepository): SpecieRepository =
        defaultSpecieRepository

}