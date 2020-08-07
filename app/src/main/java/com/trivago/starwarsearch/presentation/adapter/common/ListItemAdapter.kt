package com.trivago.starwarsearch.presentation.adapter.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.data.dto.common.ListItem

class ListItemAdapter(
    private val dataList: List<ListItem>,
    private val listener: IItemClickListener
) : RecyclerView.Adapter<ListItemAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(dataList[holder.adapterPosition], listener)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(
            listItem: ListItem,
            listener: IItemClickListener
        ) {
            itemView.setOnClickListener { listener.onClick(listItem.url) }
            itemView.findViewById<TextView>(R.id.tv_label).text = listItem.label
        }
    }

    interface IItemClickListener {
        fun onClick(url: String)
    }

}