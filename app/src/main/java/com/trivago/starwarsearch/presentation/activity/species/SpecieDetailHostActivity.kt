package com.trivago.starwarsearch.presentation.activity.species

import android.os.Bundle
import android.view.MenuItem
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.presentation.activity.BaseActivity
import com.trivago.starwarsearch.presentation.fragment.species.SpecieDetailFragment

class SpecieDetailHostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val specieUrl: String? = intent.extras?.getString(EXTRA_SPECIE_URL)

        if (specieUrl.isNullOrEmpty()) {
            finish()
            return
        }

        popAllFragments()
        startFragmentOnMaster(SpecieDetailFragment.newInstance(specieUrl), true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_SPECIE_URL = "extra_specie_url"
    }

}