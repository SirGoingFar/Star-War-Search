package com.trivago.starwarsearch.views.activity.film

import android.os.Bundle
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.views.activity.BaseActivity
import com.trivago.starwarsearch.views.fragment.film.FilmDetailFragment

class FilmDetailHostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_detail_host)

        val filmUrl: String? = intent.extras?.getString(EXTRA_FILM_URL)

        if (filmUrl.isNullOrEmpty()) {
            finish()
            return
        }

        popAllFragments()
        startFragmentOnMaster(FilmDetailFragment.newInstance(filmUrl), true)

    }

    companion object {
        const val EXTRA_FILM_URL = "extra_film_url"
    }

}