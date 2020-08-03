package com.trivago.starwarsearch.di.component

import android.content.Context
import com.trivago.starwarsearch.di.annotation.CharacterSearchScope
import com.trivago.starwarsearch.di.module.DataSourceModule
import com.trivago.starwarsearch.di.module.RepositoryModule
import com.trivago.starwarsearch.network.di.module.NetworkModule
import com.trivago.starwarsearch.views.activity.character_search.CharacterSearchHostActivity
import com.trivago.starwarsearch.views.fragment.character_detail.CharacterDetailFragment
import com.trivago.starwarsearch.views.fragment.character_search.CharacterSearchFragment
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

    fun inject(characterSearchFragment: CharacterSearchFragment)
    fun inject(characterDetailFragment: CharacterDetailFragment)
    fun inject(characterSearchHostActivity: CharacterSearchHostActivity)

}