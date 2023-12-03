package com.example.homework14

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class ViewModel : ViewModel() {
    private val _dataFlow = MutableStateFlow<List<Data>>(emptyList())
    val dataFlow: StateFlow<List<Data>> = _dataFlow.asStateFlow()


    fun addBMW() {
        viewModelScope.launch {
            _dataFlow.value = _dataFlow.value.toMutableList().also {
                it.add(
                    Data(
                        id = Random.nextInt(),
                        image = R.drawable.bmw,
                        text = "BMW m5 CS"
                    )
                )
            }
        }
    }

    fun addMercedes() {
        viewModelScope.launch {
            _dataFlow.value = _dataFlow.value.toMutableList().also {
                it.add(
                    Data(
                        id = Random.nextInt(),
                        image = R.drawable.mercedes,
                        text = "Mercedes GT63s"
                    )
                )
            }
        }
    }

    fun refreshData() {
        _dataFlow.value = emptyList()
    }

    fun deleteItem(data: Data) {
        viewModelScope.launch {
            _dataFlow.value = _dataFlow.value.toMutableList().apply {
                remove(data)
            }
        }
    }
}
