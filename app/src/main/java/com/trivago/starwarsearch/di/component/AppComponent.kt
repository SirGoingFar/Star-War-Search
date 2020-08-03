package com.trivago.starwarsearch.di.component

import android.content.Context
import com.trivago.starwarsearch.di.annotation.CharacterSearchScope
import com.trivago.starwarsearch.di.module.DataSourceModule
import com.trivago.starwarsearch.di.module.RepositoryModule
import com.trivago.starwarsearch.network.di.module.NetworkModule
import com.trivago.starwarsearch.views.fragment.CharacterDetailFragment
import com.trivago.starwarsearch.views.fragment.CharacterListFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        DataSourceModule::class
    ]
)
@CharacterSearchScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(characterListFragment: CharacterListFragment)
    fun inject(characterDetailFragment: CharacterDetailFragment)

}