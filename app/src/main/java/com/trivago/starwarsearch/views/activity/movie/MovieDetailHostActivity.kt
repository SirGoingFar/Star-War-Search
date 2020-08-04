package com.trivago.starwarsearch.views.activity.movie

import android.os.Bundle
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.views.activity.BaseActivity
import com.trivago.starwarsearch.views.fragment.movie.MovieDetailFragment

class MovieDetailHostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val filmUrl: String? = intent.extras?.getString(EXTRA_FILM_URL)

        if (filmUrl.isNullOrEmpty()) {
            finish()
            return
        }

        popAllFragments()
        startFragmentOnMaster(MovieDetailFragment.newInstance(filmUrl), true)

    }

    companion object {
        const val EXTRA_FILM_URL = "extra_film_url"
    }

}