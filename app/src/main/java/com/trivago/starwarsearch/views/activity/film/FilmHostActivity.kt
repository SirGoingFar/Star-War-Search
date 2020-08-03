package com.trivago.starwarsearch.views.activity.film

import android.content.Intent
import android.os.Bundle
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.core.utils.observe
import com.trivago.starwarsearch.di.component.AppComponent
import com.trivago.starwarsearch.views.activity.BaseInjectableActivity
import com.trivago.starwarsearch.views.fragment.film.FilmDetailFragment
import com.trivago.starwarsearch.views.fragment.film.FilmListFragment
import com.trivago.starwarsearch.views.viewaction.film.FilmHostAction
import com.trivago.starwarsearch.views.viewmodel.film.FilmHostViewModel
import com.trivago.starwarsearch.views.viewstate.film.FilmHostState
import javax.inject.Inject

class FilmHostActivity : BaseInjectableActivity<FilmHostState, FilmHostAction>() {

    @Inject
    lateinit var screenViewModel: FilmHostViewModel

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
            FilmListFragment.newInstance(intent.getStringExtra(EXTRA_CHARACTER_URL)!!),
            true
        )
    }

    override fun onPerformAction(viewAction: FilmHostAction) {
        if (viewAction is FilmHostAction.OpenFilmDetail) {
            if (isDetailViewAvailable())
                startFragmentOnDetail(FilmDetailFragment.newInstance(viewAction.url), true)
            else
                startActivity(Intent(this, FilmDetailHostActivity::class.java).apply {
                    putExtra(FilmDetailHostActivity.EXTRA_FILM_URL, viewAction.url)
                })
        }
    }

    companion object {
        const val EXTRA_CHARACTER_URL = "extra_character_url"
    }

}
