package com.example.lab6

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    var quantity = mutableIntStateOf(0)

    var flavor = mutableStateOf("")

    var date = mutableStateOf("")

    var price = mutableStateOf("")

    fun setQuantity(number: Int) {
        quantity.intValue = number
        price.value = "$${number * 5}"
    }

    fun setFlavor(value: String) {
        flavor.value = value
    }

    fun setDate(value: String) {
        date.value = value
    }

    fun reset() {
        quantity.intValue = 0
        flavor.value = ""
        date.value = ""
        price.value = ""
    }
}
