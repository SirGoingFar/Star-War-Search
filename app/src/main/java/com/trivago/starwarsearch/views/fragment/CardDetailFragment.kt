package com.trivago.starwarsearch.views.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.core.utils.observe
import com.trivago.starwarsearch.views.viewaction.CharacterDetailAction
import com.trivago.starwarsearch.views.viewmodel.CharacterDetailViewModel
import com.trivago.starwarsearch.views.viewstate.CharacterDetailState
import javax.inject.Inject

class CardDetailFragment : BaseInjectableFragment<CharacterDetailState, CharacterDetailAction>() {

    @Inject
    lateinit var screenViewModel: CharacterDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        (activity?.application as CardApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        screenViewModel.onViewCreated(arguments!!.getString(EXTRA_CARD_ID))
    }

    override fun onStateChange(viewState: CharacterDetailState) {
        viewState.character?.let {
        }
    }

    companion object {
        const val EXTRA_CARD_ID = "extra_card_id"
        fun newInstance(cardId: String) = CardDetailFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_CARD_ID, cardId)
            }
        }
    }
}