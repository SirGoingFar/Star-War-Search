package com.trivago.starwarsearch.presentation.adapter.character_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.data.dto.character_search.Character
import com.trivago.starwarsearch.presentation.adapter.BaseRecyclerViewAdapter
import com.trivago.starwarsearch.presentation.util.CharacterDiffUtil

class CharacterRecyclerViewAdapter(private val clickListener: ICharacterClickListener) :
    BaseRecyclerViewAdapter<Character, CharacterRecyclerViewAdapter.Holder>() {

    override fun getDiffResult(
        oldList: List<Character>,
        newList: List<Character>
    ): DiffUtil.DiffResult = CharacterDiffUtil()
        .computeDiff(oldList, newList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(getItemAt(holder.adapterPosition))
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(item: Character?) {

            item?.let {
                itemView.setOnClickListener { clickListener.onClick(item) }
            }

            itemView.findViewById<TextView>(R.id.tv_character_name).text = item?.name?.capitalize()
            itemView.findViewById<TextView>(R.id.tv_character_gender).text =
                item?.gender?.capitalize()

        }
    }

    interface ICharacterClickListener {
        fun onClick(character: Character)
    }

}