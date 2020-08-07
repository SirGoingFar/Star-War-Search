package com.trivago.starwarsearch.presentation.activity.character_search

import android.content.Intent
import android.os.Bundle
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.common.core.utils.observe
import com.trivago.starwarsearch.common.di.component.AppComponent
import com.trivago.starwarsearch.presentation.activity.BaseInjectableActivity
import com.trivago.starwarsearch.presentation.activity.character_detail.CharacterDetailHostActivity
import com.trivago.starwarsearch.presentation.fragment.character_detail.CharacterDetailFragment
import com.trivago.starwarsearch.presentation.fragment.character_search.CharacterSearchFragment
import com.trivago.starwarsearch.presentation.viewaction.character_search.CharacterSearchHostAction
import com.trivago.starwarsearch.presentation.viewmodel.character_search.CharacterSearchHostViewModel
import com.trivago.starwarsearch.presentation.viewstate.character_search.CharacterSearchHostViewState
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
