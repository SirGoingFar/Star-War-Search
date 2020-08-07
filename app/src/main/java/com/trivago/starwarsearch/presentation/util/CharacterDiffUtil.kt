package com.trivago.starwarsearch.presentation.util

import com.trivago.starwarsearch.data.dto.character_search.Character

class CharacterDiffUtil : BaseDiffUtil<Character>() {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getFromOldListAt(oldItemPosition)
        val newItem = getFromNewListAt(newItemPosition)

        return oldItem?.name == newItem?.name && oldItem?.gender == newItem?.gender
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getFromOldListAt(oldItemPosition)
        val newItem = getFromNewListAt(newItemPosition)
        return oldItem?.url == newItem?.url
    }

}