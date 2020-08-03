package com.trivago.starwarsearch.views.activity

import android.os.Bundle
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.views.fragment.CharacterDetailFragment

class CharacterDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        val characterUrl: String? = intent.extras?.getString(EXTRA_CHARACTER_URL)

        if (characterUrl.isNullOrEmpty()) {
            finish()
            return
        }

        popAllFragments()
        startFragmentOnMaster(CharacterDetailFragment.newInstance(characterUrl), true)
    }

    companion object {
        const val EXTRA_CHARACTER_URL = "extra_character_url"
    }
}
