package com.trivago.starwarsearch.presentation.activity.movie

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.common.core.utils.observe
import com.trivago.starwarsearch.common.di.component.AppComponent
import com.trivago.starwarsearch.presentation.activity.BaseInjectableActivity
import com.trivago.starwarsearch.presentation.fragment.movie.MovieDetailFragment
import com.trivago.starwarsearch.presentation.fragment.movie.MovieListFragment
import com.trivago.starwarsearch.presentation.viewaction.movie.MovieHostAction
import com.trivago.starwarsearch.presentation.viewmodel.movie.MovieHostViewModel
import com.trivago.starwarsearch.presentation.viewstate.movie.MovieHostState
import javax.inject.Inject

class MovieHostActivity : BaseInjectableActivity<MovieHostState, MovieHostAction>() {

    @Inject
    lateinit var screenViewModel: MovieHostViewModel

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as StarWarSearchApplication).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        popAllFragments()
        startFragmentOnMaster(
            MovieListFragment.newInstance(intent.getStringExtra(EXTRA_CHARACTER_URL)!!),
            true
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
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
