package com.trivago.starwarsearch.views.activity

import android.os.Bundle
import com.trivago.starwarsearch.views.fragment.CharacterListFragment

class CharacterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popAllFragments()
        startFragment(CharacterListFragment.newInstance(), true)
    }
}
