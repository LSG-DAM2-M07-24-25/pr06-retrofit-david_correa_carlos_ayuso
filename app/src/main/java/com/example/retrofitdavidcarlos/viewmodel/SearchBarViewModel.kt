package com.example.retrofitdavidcarlos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchBarViewModel : ViewModel() {
    private val _searchedText = MutableLiveData("")
    val searchedText: LiveData<String> = _searchedText
    private val _searchHistory = MutableLiveData<List<String>>(emptyList())
    val searchHistory: LiveData<List<String>> = _searchHistory

    fun onSearchTextChange(text: String) {
        _searchedText.value = text
    }

    fun addToHistory(text: String) {
        if (text.isNotBlank()) {
            val currentHistory = _searchHistory.value.orEmpty()
            _searchHistory.value = listOf(text) + currentHistory
            _searchedText.value = ""
        }
    }

    fun clearHistory() {
        _searchHistory.value = emptyList()
    }

}