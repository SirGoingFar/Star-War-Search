package com.trivago.starwarsearch.presentation.adapter

import android.util.Log
import androidx.annotation.MainThread
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.trivago.starwarsearch.common.core.utils.runBackground
import com.trivago.starwarsearch.common.core.utils.runOnMainThread
import java.util.*
import kotlin.collections.ArrayList


abstract class BaseRecyclerViewAdapter<Type : Any, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VH>() {

    private var isCurrentlyProcessing: Boolean = false
    private val mPendingUpdates: ArrayDeque<List<Type>> = ArrayDeque()
    private var opsThread: Thread? = null

    private var mDataset: List<Type> = emptyList()


    override fun getItemCount(): Int {
        return getUpdatedList().size
    }

    @MainThread
    open fun updateDataset(newList: List<Type>) {
        mPendingUpdates.add(newList)
        if (mPendingUpdates.size == 1 && !isCurrentlyProcessing)
            internalUpdate(newList)
    }

    private fun internalUpdate(newList: List<Type>) {

        isCurrentlyProcessing = true

        opsThread = runBackground {
            val result = getDiffResult(mDataset, newList)

            //back to main thread for the update
            runOnMainThread {

                mDataset = newList.toMutableList()

                result.dispatchUpdatesTo(this)


                if (mPendingUpdates.isEmpty())
                    return@runOnMainThread

                //We are done with this dataset
                mPendingUpdates.remove()

                isCurrentlyProcessing = false

                //Process the next queued dataset if any
                if (!mPendingUpdates.isEmpty())
                    internalUpdate(mPendingUpdates.peek()!!)
            }
        }

        opsThread!!.start()
        opsThread!!.join()
    }

    private fun clearPendingUpdates() {
        //stop current update in process
        opsThread?.interrupt()

        //clear pending updates
        mPendingUpdates.clear()
    }

    protected fun getUpdatedList(): List<Type> {
        return if (mPendingUpdates.isEmpty())
            mDataset
        else
            mPendingUpdates.peekLast()!!
    }

    fun getDataList():List<Type>{
        return getUpdatedList()
    }

    private fun isValidIndex(index: Int): Boolean = index >= 0 && index <= (itemCount - 1)

    fun removeItemAt(index: Int) {
        if (!isValidIndex(index))
            return

        val newList = ArrayList(getUpdatedList())
        newList.removeAt(index)
        updateDataset(newList)
    }

    fun addItemAtIndex(index: Int, item: Type) {
        if (!isValidIndex(index))
            return

        val newList = ArrayList(getUpdatedList())
        newList.add(index, item)
        updateDataset(newList)
    }

    fun moveItemTo(fromPosition: Int, toPosition: Int) {
        if (!isValidIndex(fromPosition) || !isValidIndex(toPosition))
            return

        val currentList = ArrayList(getUpdatedList().toMutableList())

        //Retrieve the item object to move
        val itemToMove: Type = currentList[fromPosition]

        //remove the item from the specified position
        currentList.removeAt(fromPosition)

        //move the item to the position
        currentList.add(toPosition, itemToMove)

        updateDataset(currentList)
    }

    fun swapAdapterData(newList: List<Type>) {
        clearPendingUpdates()
        updateDataset(newList)
    }

    fun addMoreItems(moreItemList: List<Type>) {

        //get the current dataset
        val currentItems = ArrayList<Type>(getUpdatedList().toMutableList())

        //add the new list
        currentItems.addAll(moreItemList)

        //update the dataset
        updateDataset(currentItems)

    }

    fun getItemAt(position: Int): Type? {
        return if (!isValidIndex(position)) {
            Log.e("TAG", String.format("Invalid index %d", position))
            null
        } else getUpdatedList()[position]
    }


    abstract fun getDiffResult(
        oldList: List<Type>,
        newList: List<Type>
    ): DiffUtil.DiffResult

}