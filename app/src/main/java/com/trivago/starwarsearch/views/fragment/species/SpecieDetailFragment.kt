package com.trivago.starwarsearch.views.fragment.species

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.core.extension.show
import com.trivago.starwarsearch.core.utils.observe
import com.trivago.starwarsearch.views.fragment.BaseInjectableFragment
import com.trivago.starwarsearch.views.viewaction.species.SpecieDetailAction
import com.trivago.starwarsearch.views.viewmodel.species.SpecieDetailViewModel
import com.trivago.starwarsearch.views.viewstate.species.SpecieDetailState
import kotlinx.android.synthetic.main.fragment_specie_detail.*
import javax.inject.Inject

class SpecieDetailFragment : BaseInjectableFragment<SpecieDetailState, SpecieDetailAction>() {

    @Inject
    lateinit var screenViewModel: SpecieDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as StarWarSearchApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        screenViewModel.onViewCreated(arguments!!.getString(ARG_SPECIE_URL))
    }

    override fun onStateChange(viewState: SpecieDetailState) {
        with(viewState) {
            pbr_loader.show(showLoader)
            nsv_filled.show(!showLoader)

            specie?.let {
                tv_name_value.text = it.name
                tv_language_value.text = it.language
                tv_homeworld_population_value.text =
                    if (it.population.isNullOrEmpty()) "-" else it.population
            }
        }
    }

    override fun onPerformAction(viewAction: SpecieDetailAction) {
        if(viewAction is SpecieDetailAction.SpecieDetailFetchUnsuccessful){
            toast(viewAction.msg)
            pbr_loader.show(false)
        }
    }

    companion object {
        private const val ARG_SPECIE_URL = "arg_specie_url"
        fun newInstance(specieUrl: String) = SpecieDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_SPECIE_URL, specieUrl)
            }
        }
    }

}