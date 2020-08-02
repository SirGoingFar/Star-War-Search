package com.trivago.starwarsearch.views.util

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtil<Type : Any> : DiffUtil.Callback() {

    protected var oldList: List<Type> = emptyList()
    protected var newList: List<Type> = emptyList()

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    fun computeDiff(
        oldList: List<Type>,
        newList: List<Type>,
        detectMoves: Boolean = false
    ): DiffUtil.DiffResult {
        this.oldList = oldList
        this.newList = newList

        return DiffUtil.calculateDiff(this, detectMoves)
    }

    fun getFromOldListAt(index: Int): Type? {
        if (oldList.isEmpty() || index < 0 || index > (oldList.size - 1))
            return null

        return oldList[index]
    }

    fun getFromNewListAt(index: Int): Type? {
        if (newList.isEmpty() || index < 0 || index > (newList.size - 1))
            return null

        return newList[index]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any?{

        //Todo: If more than a view can change, consider having enum for each of the views/actions...
        // then return a list of enum here


        return  super.getChangePayload(oldItemPosition, newItemPosition)
    }

    abstract override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean

    abstract override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean

}