package com.trivago.starwarsearch.presentation.fragment.movie

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
import com.trivago.starwarsearch.presentation.viewaction.movie.MovieDetailAction
import com.trivago.starwarsearch.presentation.viewmodel.movie.MovieDetailViewModel
import com.trivago.starwarsearch.presentation.viewstate.movie.MovieDetailState
import kotlinx.android.synthetic.main.fragment_film_detail.*
import javax.inject.Inject

class MovieDetailFragment : BaseInjectableFragment<MovieDetailState, MovieDetailAction>() {

    @Inject
    lateinit var screenViewModel: MovieDetailViewModel

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

        screenViewModel.onViewCreated(arguments!!.getString(ARG_FILM_URL))
    }

    override fun onStateChange(viewState: MovieDetailState) {
        with(viewState) {
            pb_loader.show(showLoader)
            ns_filled.show(!showLoader && errorMsg.isNullOrEmpty())
            errorMsg?.let { toast(it) }

            film?.let {
                tv_title_value.text = it.title
                tv_crawl_value.text = it.openingCrawl
            }
        }
    }

    companion object {
        const val ARG_FILM_URL = "arg_film_url"

        fun newInstance(filmUrl: String) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_FILM_URL, filmUrl)
            }
        }
    }

}