package com.trivago.starwarsearch.views.activity.movie

import android.content.Intent
import android.os.Bundle
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.core.utils.observe
import com.trivago.starwarsearch.di.component.AppComponent
import com.trivago.starwarsearch.views.activity.BaseInjectableActivity
import com.trivago.starwarsearch.views.fragment.movie.MovieDetailFragment
import com.trivago.starwarsearch.views.fragment.movie.MovieListFragment
import com.trivago.starwarsearch.views.viewaction.movie.MovieHostAction
import com.trivago.starwarsearch.views.viewmodel.movie.MovieHostViewModel
import com.trivago.starwarsearch.views.viewstate.movie.MovieHostState
import javax.inject.Inject

class MovieHostActivity : BaseInjectableActivity<MovieHostState, MovieHostAction>() {

    @Inject
    lateinit var screenViewModel: MovieHostViewModel

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as StarWarSearchApplication).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        popAllFragments()
        startFragmentOnMaster(
            MovieListFragment.newInstance(intent.getStringExtra(EXTRA_CHARACTER_URL)!!),
            true
        )
    }

    override fun onPerformAction(viewAction: MovieHostAction) {
        if (viewAction is MovieHostAction.OpenFilmDetail) {
            if (isDetailViewAvailable())
                startFragmentOnDetail(MovieDetailFragment.newInstance(viewAction.url), true)
            else
                startActivity(Intent(this, MovieDetailHostActivity::class.java).apply {
                    putExtra(MovieDetailHostActivity.EXTRA_FILM_URL, viewAction.url)
                })
        }
    }

    companion object {
        const val EXTRA_CHARACTER_URL = "extra_character_url"
    }

}
