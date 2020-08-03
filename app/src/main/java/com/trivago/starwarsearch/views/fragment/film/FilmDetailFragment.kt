package com.trivago.starwarsearch.views.fragment.film

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
import com.trivago.starwarsearch.views.viewaction.film.FilmDetailAction
import com.trivago.starwarsearch.views.viewmodel.film.FilmDetailViewModel
import com.trivago.starwarsearch.views.viewstate.film.FilmDetailState
import kotlinx.android.synthetic.main.fragment_film_detail.*
import javax.inject.Inject

class FilmDetailFragment : BaseInjectableFragment<FilmDetailState, FilmDetailAction>() {

    @Inject
    lateinit var screenViewModel: FilmDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as StarWarSearchApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        screenViewModel.onViewCreated(arguments!!.getString(ARG_FILM_URL))
    }

    override fun onStateChange(viewState: FilmDetailState) {
        with(viewState) {
            pb_loader.show(showLoader)
            ns_filled.show(!showLoader)
            film?.let {
                tv_title_value.text = it.title
                tv_crawl_value.text = it.openingCrawl
            }
        }
    }

    override fun onPerformAction(viewAction: FilmDetailAction) {
        if (viewAction is FilmDetailAction.FilmDetailLoadError) {
            toast(viewAction.msg)
            activity?.finish()
        }
    }

    companion object {
        const val ARG_FILM_URL = "arg_film_url"

        fun newInstance(filmUrl: String) = FilmDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_FILM_URL, filmUrl)
            }
        }
    }

}