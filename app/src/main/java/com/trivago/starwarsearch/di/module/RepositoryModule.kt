package com.trivago.starwarsearch.di.module

import com.trivago.starwarsearch.di.annotation.CharacterSearchScope
import com.trivago.starwarsearch.domain.dto.film.DefaultFilmRepository
import com.trivago.starwarsearch.domain.repository.character.CharacterRepository
import com.trivago.starwarsearch.domain.repository.character.DefaultCharacterRepository
import com.trivago.starwarsearch.domain.repository.film.FilmRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @CharacterSearchScope
    @Provides
    fun provideCharacterRepository(defaultCharacterRepository: DefaultCharacterRepository): CharacterRepository = defaultCharacterRepository

    @CharacterSearchScope
    @Provides
    fun provideFilmRepository(defaultFilmRepository: DefaultFilmRepository):FilmRepository = defaultFilmRepository

}