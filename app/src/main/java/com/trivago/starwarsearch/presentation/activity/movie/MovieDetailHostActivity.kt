package com.trivago.starwarsearch.presentation.activity.movie

import android.os.Bundle
import android.view.MenuItem
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.presentation.activity.BaseActivity
import com.trivago.starwarsearch.presentation.fragment.movie.MovieDetailFragment

class MovieDetailHostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val filmUrl: String? = intent.extras?.getString(EXTRA_FILM_URL)

        if (filmUrl.isNullOrEmpty()) {
            finish()
            return
        }

        popAllFragments()
        startFragmentOnMaster(MovieDetailFragment.newInstance(filmUrl), true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_FILM_URL = "extra_film_url"
    }

}