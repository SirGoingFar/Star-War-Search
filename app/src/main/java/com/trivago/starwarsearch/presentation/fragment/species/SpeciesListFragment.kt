package com.trivago.starwarsearch.presentation.fragment.species

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.common.core.utils.observe
import com.trivago.starwarsearch.presentation.activity.species.SpeciesHostActivity
import com.trivago.starwarsearch.presentation.adapter.common.ListItemAdapter
import com.trivago.starwarsearch.presentation.fragment.BaseInjectableFragment
import com.trivago.starwarsearch.presentation.viewaction.species.SpeciesListAction
import com.trivago.starwarsearch.presentation.viewmodel.species.SpeciesListViewModel
import com.trivago.starwarsearch.presentation.viewstate.species.SpeciesListState
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class SpeciesListFragment : BaseInjectableFragment<SpeciesListState, SpeciesListAction>(),
    ListItemAdapter.IItemClickListener {

    @Inject
    lateinit var screenViewModel: SpeciesListViewModel

    private lateinit var speciesHostActivity: SpeciesHostActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SpeciesHostActivity)
            speciesHostActivity = context

        speciesHostActivity.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        screenViewModel.onViewCreated(arguments!!.getString(ARG_CHARACTER_URL))
    }

    override fun onStateChange(viewState: SpeciesListState) {
        with(viewState) {
            rv_items.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv_items.setHasFixedSize(true)
            rv_items.adapter = ListItemAdapter(list, this@SpeciesListFragment)
        }
    }

    override fun onClick(url: String) {
        speciesHostActivity.screenViewModel.onSpecieClicked(url)
    }

    companion object {
        private const val ARG_CHARACTER_URL = "arg_character_url"
        fun newInstance(characterUrl: String) = SpeciesListFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CHARACTER_URL, characterUrl)
            }
        }
    }

}