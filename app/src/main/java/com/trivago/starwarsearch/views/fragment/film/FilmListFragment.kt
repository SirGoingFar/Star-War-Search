package com.trivago.starwarsearch.views.fragment.film

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.core.utils.observe
import com.trivago.starwarsearch.views.activity.film.FilmHostActivity
import com.trivago.starwarsearch.views.adapter.common.ListItemAdapter
import com.trivago.starwarsearch.views.fragment.BaseInjectableFragment
import com.trivago.starwarsearch.views.viewaction.film.FilmListViewAction
import com.trivago.starwarsearch.views.viewmodel.film.FilmListViewModel
import com.trivago.starwarsearch.views.viewstate.film.FilmListState
import kotlinx.android.synthetic.main.fragment_film_list.*
import javax.inject.Inject

class FilmListFragment : BaseInjectableFragment<FilmListState, FilmListViewAction>(),
    ListItemAdapter.IItemClickListener {

    @Inject
    lateinit var screenViewModel: FilmListViewModel

    private lateinit var filmHostActivity: FilmHostActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FilmHostActivity)
            filmHostActivity = context

        filmHostActivity.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        screenViewModel.onViewCreated(arguments!!.getString(ARG_CHARACTER_URL))
    }

    override fun onStateChange(viewState: FilmListState) {
        with(viewState) {
            rv_film.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv_film.setHasFixedSize(true)
            rv_film.adapter = ListItemAdapter(list, this@FilmListFragment)
        }
    }

    override fun onClick(url: String) {
        filmHostActivity.screenViewModel.onFilmClicked(url)
    }

    companion object {
        private const val ARG_CHARACTER_URL = "arg_character_url"
        fun newInstance(characterUrl: String) = FilmListFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CHARACTER_URL, characterUrl)
            }
        }
    }

}