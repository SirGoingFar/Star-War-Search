package com.trivago.starwarsearch.presentation.fragment.species

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.common.core.extension.show
import com.trivago.starwarsearch.common.core.utils.observe
import com.trivago.starwarsearch.presentation.fragment.BaseInjectableFragment
import com.trivago.starwarsearch.presentation.viewaction.species.SpecieDetailAction
import com.trivago.starwarsearch.presentation.viewmodel.species.SpecieDetailViewModel
import com.trivago.starwarsearch.presentation.viewstate.species.SpecieDetailState
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

        screenViewModel.onViewCreated(arguments!!.getString(ARG_SPECIE_URL))
    }

    override fun onStateChange(viewState: SpecieDetailState) {
        with(viewState) {
            pbr_loader.show(showLoader)
            nsv_filled.show(!showLoader && errorMsg.isNullOrEmpty())
            errorMsg?.let { toast(it) }

            specie?.let {
                tv_name_value.text = it.name
                tv_language_value.text = it.language
                tv_homeworld_name_value.text =
                    if (it.homeWorldName.isNullOrEmpty()) "-" else it.homeWorldName
                tv_homeworld_population_value.text =
                    if (it.population.isNullOrEmpty()) "-" else it.population
            }
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