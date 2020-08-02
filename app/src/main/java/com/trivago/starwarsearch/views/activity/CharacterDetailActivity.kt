package com.trivago.starwarsearch.views.activity

import android.os.Bundle
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.views.fragment.CardDetailFragment

class CharacterDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        val cardId: String? = intent.extras?.getString(EXTRA_CARD_ID)

        if (cardId.isNullOrEmpty()) {
            finish()
            return
        }

        popAllFragments()
        startFragment(CardDetailFragment.newInstance(cardId), true)
    }

    companion object {
        const val EXTRA_CARD_ID = "extra_card_id"
    }
}
