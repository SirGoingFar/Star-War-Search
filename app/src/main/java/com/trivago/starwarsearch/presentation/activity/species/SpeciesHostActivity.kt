package com.trivago.starwarsearch.presentation.activity.species

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.common.core.utils.observe
import com.trivago.starwarsearch.common.di.component.AppComponent
import com.trivago.starwarsearch.presentation.activity.BaseInjectableActivity
import com.trivago.starwarsearch.presentation.fragment.species.SpecieDetailFragment
import com.trivago.starwarsearch.presentation.fragment.species.SpeciesListFragment
import com.trivago.starwarsearch.presentation.viewaction.species.SpeciesHostAction
import com.trivago.starwarsearch.presentation.viewmodel.species.SpeciesHostViewModel
import com.trivago.starwarsearch.presentation.viewstate.species.SpeciesHostState
import javax.inject.Inject

class SpeciesHostActivity : BaseInjectableActivity<SpeciesHostState, SpeciesHostAction>() {

    @Inject
    lateinit var screenViewModel: SpeciesHostViewModel

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as StarWarSearchApplication).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        popAllFragments()
        startFragmentOnMaster(
            SpeciesListFragment.newInstance(intent.getStringExtra(EXTRA_CHARACTER_URL)!!),
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

    override fun onPerformAction(viewAction: SpeciesHostAction) {
        if (viewAction is SpeciesHostAction.OpenSpecieDetail) {
            if (isDetailViewAvailable())
                startFragmentOnDetail(SpecieDetailFragment.newInstance(viewAction.url), true)
            else
                startActivity(Intent(this, SpecieDetailHostActivity::class.java).apply {
                    putExtra(SpecieDetailHostActivity.EXTRA_SPECIE_URL, viewAction.url)
                })
        }
    }


    companion object {
        const val EXTRA_CHARACTER_URL = "extra_character_url"
    }

}