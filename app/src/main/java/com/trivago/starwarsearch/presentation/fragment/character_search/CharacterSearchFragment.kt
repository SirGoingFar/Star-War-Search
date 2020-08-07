package com.trivago.starwarsearch.presentation.fragment.character_search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.trivago.starwarsearch.presentation.adapter.LinearLayoutManagerWrapper
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.common.core.extension.show
import com.trivago.starwarsearch.common.core.utils.observe
import com.trivago.starwarsearch.data.dto.character_search.Character
import com.trivago.starwarsearch.presentation.activity.character_search.CharacterSearchHostActivity
import com.trivago.starwarsearch.presentation.adapter.character_search.CharacterRecyclerViewAdapter
import com.trivago.starwarsearch.presentation.fragment.BaseInjectableFragment
import com.trivago.starwarsearch.presentation.util.addInfiniteScrollListener
import com.trivago.starwarsearch.presentation.viewaction.character_search.CharacterSearchAction
import com.trivago.starwarsearch.presentation.viewmodel.character_search.CharacterSearchViewModel
import com.trivago.starwarsearch.presentation.viewstate.character_search.CharacterSearchState
import kotlinx.android.synthetic.main.fragment_character_list.*
import javax.inject.Inject


class CharacterSearchFragment :
    BaseInjectableFragment<CharacterSearchState, CharacterSearchAction>(),
    SearchView.OnQueryTextListener, CharacterRecyclerViewAdapter.ICharacterClickListener {

    @Inject
    lateinit var screenViewModel: CharacterSearchViewModel

    private var mSearchView: SearchView? = null

    private lateinit var characterActivity: CharacterSearchHostActivity

    private lateinit var mAdapter: CharacterRecyclerViewAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //Get an instance of the parent activity
        if (context is CharacterSearchHostActivity)
            characterActivity = context

        //Inject fragment's dependencies
        characterActivity.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe State Change
        observe(screenViewModel.stateLiveData, stateObserver)

        //Setup toolbar
        characterActivity.setSupportActionBar(search_toolbar)
        characterActivity.supportActionBar!!.title = ""
        characterActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Setup RecyclerView
        rv_search_result.layoutManager =
            LinearLayoutManagerWrapper(context!!, LinearLayoutManager.VERTICAL, false)
        rv_search_result.setHasFixedSize(true)
        mAdapter =
            CharacterRecyclerViewAdapter(
                this
            )
        rv_search_result.adapter = mAdapter
        rv_search_result.addInfiniteScrollListener { screenViewModel.onListScrollToBase() }

        screenViewModel.onViewCreated()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)

        //Inflate Menu resource
        inflater.inflate(R.menu.menu_search, menu)

        //Setup the Search field
        val searchItem = menu.findItem(R.id.action_search)
        mSearchView = searchItem!!.actionView as SearchView
        val searchManager = context!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchView?.isIconifiedByDefault = false
        mSearchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        mSearchView?.requestFocus()
        mSearchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            baseActivity?.finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStateChange(viewState: CharacterSearchState) {
        //change view data
        with(viewState) {
            pbLoader.show(showLoader)
            mSearchView?.setQuery(searchTerm, false)

            if (showEmptyState) {
                tv_title.text = title
                tv_body.text = body
                rv_search_result.show(false)
                container_empty.show(true)
            } else {
                mAdapter.updateDataset(characterList)
                rv_search_result.show(true)
            }

        }
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        screenViewModel.onSearchTextChanged(text)
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        screenViewModel.onSearchTextChanged(text)
        return true
    }

    override fun onClick(character: Character) {
        characterActivity.screenViewModel.onCharacterClicked(character.url)
    }

    companion object {
        fun newInstance() =
            CharacterSearchFragment()
    }

}