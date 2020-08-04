package com.trivago.starwarsearch.views.activity.species

import android.os.Bundle
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.views.activity.BaseActivity
import com.trivago.starwarsearch.views.fragment.species.SpecieDetailFragment

class SpecieDetailHostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val specieUrl: String? = intent.extras?.getString(EXTRA_SPECIE_URL)

        if (specieUrl.isNullOrEmpty()) {
            finish()
            return
        }

        popAllFragments()
        startFragmentOnMaster(SpecieDetailFragment.newInstance(specieUrl), true)

    }

    companion object {
        const val EXTRA_SPECIE_URL = "extra_specie_url"
    }

}