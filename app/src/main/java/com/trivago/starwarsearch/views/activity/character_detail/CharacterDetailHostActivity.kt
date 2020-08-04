package com.trivago.starwarsearch.views.activity.character_detail

import android.os.Bundle
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.views.activity.BaseActivity
import com.trivago.starwarsearch.views.fragment.character_detail.CharacterDetailFragment

class CharacterDetailHostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

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
