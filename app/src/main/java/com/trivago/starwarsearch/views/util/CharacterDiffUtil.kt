package com.trivago.starwarsearch.views.util

import com.trivago.starwarsearch.domain.dto.Character

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