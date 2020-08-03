package com.trivago.starwarsearch.views.fragment.character_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.StarWarSearchApplication
import com.trivago.starwarsearch.core.extension.show
import com.trivago.starwarsearch.core.utils.observe
import com.trivago.starwarsearch.views.activity.film.FilmHostActivity
import com.trivago.starwarsearch.views.fragment.BaseInjectableFragment
import com.trivago.starwarsearch.views.fragment.film.FilmListFragment
import com.trivago.starwarsearch.views.viewaction.character_detail.CharacterDetailAction
import com.trivago.starwarsearch.views.viewmodel.character_detail.CharacterDetailViewModel
import com.trivago.starwarsearch.views.viewstate.character_detail.CharacterDetailState
import kotlinx.android.synthetic.main.fragment_character_detail.*
import javax.inject.Inject

class CharacterDetailFragment :
    BaseInjectableFragment<CharacterDetailState, CharacterDetailAction>() {

    @Inject
    lateinit var screenViewModel: CharacterDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as StarWarSearchApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //observe Action
        observe(screenViewModel.actionLiveData, actionObserver)

        screenViewModel.onViewCreated(arguments!!.getString(EXTRA_CHARACTER_URL))
    }

    override fun onStateChange(viewState: CharacterDetailState) {
        with(viewState) {
            tv_name_value.text = name
            tv_gender_value.text = gender
            tv_height_value.text = height
            tv_birth_year_value.text = birthYear
            btn_see_films.show(hasFilm)
            btn_see_species.show(hasSpecies)

            btn_see_species.setOnClickListener { }

            btn_see_films.setOnClickListener {
                startActivity(
                    Intent(
                        context,
                        FilmHostActivity::class.java
                    ).apply {
                        putExtra(FilmHostActivity.EXTRA_CHARACTER_URL, url)
                    }
                )
            }
        }
    }

    companion object {
        const val EXTRA_CHARACTER_URL = "extra_character_url"
        fun newInstance(characterUrl: String) = CharacterDetailFragment()
            .apply {
                arguments = Bundle().apply {
                    putString(EXTRA_CHARACTER_URL, characterUrl)
                }
            }
    }
}