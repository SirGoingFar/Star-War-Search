package com.trivago.starwarsearch.views.fragment.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.core.utils.observe
import com.trivago.starwarsearch.views.activity.movie.MovieHostActivity
import com.trivago.starwarsearch.views.adapter.common.ListItemAdapter
import com.trivago.starwarsearch.views.fragment.BaseInjectableFragment
import com.trivago.starwarsearch.views.viewaction.movie.MovieListViewAction
import com.trivago.starwarsearch.views.viewmodel.movie.MovieListViewModel
import com.trivago.starwarsearch.views.viewstate.movie.MovieListState
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class MovieListFragment : BaseInjectableFragment<MovieListState, MovieListViewAction>(),
    ListItemAdapter.IItemClickListener {

    @Inject
    lateinit var screenViewModel: MovieListViewModel

    private lateinit var filmHostActivity: MovieHostActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieHostActivity)
            filmHostActivity = context

        filmHostActivity.appComponent.inject(this)
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

    override fun onStateChange(viewState: MovieListState) {
        with(viewState) {
            rv_items.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv_items.setHasFixedSize(true)
            rv_items.adapter = ListItemAdapter(list, this@MovieListFragment)
        }
    }

    override fun onClick(url: String) {
        filmHostActivity.screenViewModel.onFilmClicked(url)
    }

    companion object {
        private const val ARG_CHARACTER_URL = "arg_character_url"
        fun newInstance(characterUrl: String) = MovieListFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CHARACTER_URL, characterUrl)
            }
        }
    }

}