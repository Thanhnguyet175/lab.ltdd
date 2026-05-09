package com.example.lab5.data

import com.example.lab5.R

class Datasource {

    fun loadDogs(): List<Dog> {
        return listOf(
            Dog(R.drawable.dog_1, R.string.nini, 7),
            Dog(R.drawable.dog_2, R.string.mimi, 6),
            Dog(R.drawable.dog_3, R.string.bella, 4),
            Dog(R.drawable.dog_4, R.string.ben, 3),
            Dog(R.drawable.dog_5, R.string.honey, 2),
            )
    }
}