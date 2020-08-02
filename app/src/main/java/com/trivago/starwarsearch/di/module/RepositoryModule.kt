package com.trivago.starwarsearch.di.module

import com.trivago.starwarsearch.di.annotation.CharacterSearchScope
import com.trivago.starwarsearch.domain.repository.CharacterRepository
import com.trivago.starwarsearch.domain.repository.DefaultCharacterRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @CharacterSearchScope
    @Provides
    fun provideCardRepository(defaultCardRepository: DefaultCharacterRepository): CharacterRepository = defaultCardRepository

}