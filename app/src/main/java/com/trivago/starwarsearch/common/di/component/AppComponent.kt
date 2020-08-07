package com.trivago.starwarsearch.common.di.component

import android.content.Context
import com.trivago.starwarsearch.common.di.annotation.SearchScope
import com.trivago.starwarsearch.common.di.module.DataSourceModule
import com.trivago.starwarsearch.common.di.module.RepositoryModule
import com.trivago.starwarsearch.common.network.di.module.NetworkModule
import com.trivago.starwarsearch.presentation.activity.character_search.CharacterSearchHostActivity
import com.trivago.starwarsearch.presentation.activity.movie.MovieHostActivity
import com.trivago.starwarsearch.presentation.activity.species.SpeciesHostActivity
import com.trivago.starwarsearch.presentation.fragment.character_detail.CharacterDetailFragment
import com.trivago.starwarsearch.presentation.fragment.character_search.CharacterSearchFragment
import com.trivago.starwarsearch.presentation.fragment.movie.MovieDetailFragment
import com.trivago.starwarsearch.presentation.fragment.movie.MovieListFragment
import com.trivago.starwarsearch.presentation.fragment.species.SpecieDetailFragment
import com.trivago.starwarsearch.presentation.fragment.species.SpeciesListFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        DataSourceModule::class
    ]
)
@SearchScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(characterSearchFragment: CharacterSearchFragment)
    fun inject(characterDetailFragment: CharacterDetailFragment)
    fun inject(characterSearchHostActivity: CharacterSearchHostActivity)
    fun inject(filmHostActivity: MovieHostActivity)
    fun inject(filmListFragment: MovieListFragment)
    fun inject(filmDetailFragment: MovieDetailFragment)
    fun inject(speciesHostActivity: SpeciesHostActivity)
    fun inject(speciesListFragment: SpeciesListFragment)
    fun inject(specieDetailFragment: SpecieDetailFragment)

}