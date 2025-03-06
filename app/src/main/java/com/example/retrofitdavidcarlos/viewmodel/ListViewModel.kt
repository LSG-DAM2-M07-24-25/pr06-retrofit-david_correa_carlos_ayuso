package com.example.retrofitdavidcarlos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitdavidcarlos.model.Lista

class ListViewModel: ViewModel() {
    private val _listas = MutableLiveData<List<Lista>>()
    val listas: LiveData<List<Lista>> = _listas
    private val _maxListas = 10
    val maxListas = _maxListas

    init {
        _listas.value = listOf(
            Lista(
                id = "1",
                name = "Jugando",
                itemCount = 0,
                isDefault = true
            ),
            Lista(
                id = "2",
                name = "Jugado",
                itemCount = 0,
                isDefault = true
            ),
            Lista(
                id = "3",
                name = "Pendiente",
                itemCount = 0,
                isDefault = true
            )
        )
    }

    fun crearLista(name: String) {
        if ((_listas.value?.size ?: 0) < _maxListas) {
            val currentLists = _listas.value ?: emptyList()
            val newList = Lista(
                id = System.currentTimeMillis().toString(),
                name = name,
                itemCount = 0,
                isDefault = false
            )

            val updatedLists = currentLists + newList
            _listas.value = updatedLists
        }
    }

    fun eliminarLista(id: String) {
        val currentLists = _listas.value ?: emptyList()
        val updatedLists = currentLists.filter { it.id != id || it.isDefault }
        _listas.value = updatedLists
    }
}