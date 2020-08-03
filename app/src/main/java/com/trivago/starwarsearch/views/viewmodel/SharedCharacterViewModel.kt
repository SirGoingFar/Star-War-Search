package com.trivago.starwarsearch.views.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SharedCharacterViewModel(application: Application) : AndroidViewModel(application) {

    private val selectedCardMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val selectedCardLiveData: LiveData<String> = selectedCardMutableLiveData

    fun onCardClicked(cardId: String) {
        selectedCardMutableLiveData.value = cardId
    }

}