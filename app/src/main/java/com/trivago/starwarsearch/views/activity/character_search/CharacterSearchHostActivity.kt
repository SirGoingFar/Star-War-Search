package com.trivago.starwarsearch.views.activity.character_search

import android.content.Intent
import android.os.Bundle
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.core.utils.observe
import com.trivago.starwarsearch.di.component.AppComponent
import com.trivago.starwarsearch.views.activity.BaseInjectableActivity
import com.trivago.starwarsearch.views.activity.character_detail.CharacterDetailHostActivity
import com.trivago.starwarsearch.views.fragment.character_detail.CharacterDetailFragment
import com.trivago.starwarsearch.views.fragment.character_search.CharacterSearchFragment
import com.trivago.starwarsearch.views.viewaction.character_search.CharacterSearchHostAction
import com.trivago.starwarsearch.views.viewmodel.character_search.CharacterSearchHostViewModel
import com.trivago.starwarsearch.views.viewstate.character_search.CharacterSearchHostViewState
import javax.inject.Inject

class CharacterSearchHostActivity :
    BaseInjectableActivity<CharacterSearchHostViewState, CharacterSearchHostAction>() {

    @Inject
    lateinit var screenViewModel: CharacterSearchHostViewModel

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
        startFragmentOnMaster(CharacterSearchFragment.newInstance(), true)
    }

    override fun onPerformAction(viewAction: CharacterSearchHostAction) {
        if (viewAction is CharacterSearchHostAction.OpenCharacterDetail) {
            if (isDetailViewAvailable()) {
                startFragmentOnDetail(
                    CharacterDetailFragment.newInstance(viewAction.characterUrl),
                    true
                )
            } else {
                startActivity(
                    Intent(
                        this,
                        CharacterDetailHostActivity::class.java
                    ).apply {
                        putExtra(
                            CharacterDetailHostActivity.EXTRA_CHARACTER_URL,
                            viewAction.characterUrl
                        )
                    }
                )
            }
        }
    }
}
