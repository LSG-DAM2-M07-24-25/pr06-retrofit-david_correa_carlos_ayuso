package com.example.retrofitdavidcarlos.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitdavidcarlos.api.Repository
import com.example.retrofitdavidcarlos.model.GameResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApiViewModel : ViewModel() {

    private val repository = Repository()
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _games = MutableLiveData<GameResponse>()
    val games = _games

    fun getGames() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getAllGames()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        _games.value = response.body()
                        _loading.value = false
                    } else {
                        Log.e("Error", "Response not successful: ${response.message()}")
                        _loading.value = false  // Still need to hide loading on error
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", "Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    _loading.value = false  // Hide loading on error
                }
            }
        }
    }
}