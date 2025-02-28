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
                itemCount = 23,
                isDefault = true
            ),
            Lista(
                id = "2",
                name = "Jugado",
                itemCount = 7,
                isDefault = true
            ),
            Lista(
                id = "3",
                name = "Pendiente",
                itemCount = 14,
                isDefault = true
            )
        )
    }

    fun crearLista(name: String) {
        if (_listas.value?.size ?: 0 < _maxListas) {
            val newList = Lista(
                id = System.currentTimeMillis().toString(),
                name = name,
                itemCount = 0,
            )
            _listas.value = _listas.value?.plus(newList) ?: listOf(newList)
        }
    }

    fun eliminarLista(id: String) {
        _listas.value = _listas.value?.filter { it.id != id || it.isDefault }
    }
}