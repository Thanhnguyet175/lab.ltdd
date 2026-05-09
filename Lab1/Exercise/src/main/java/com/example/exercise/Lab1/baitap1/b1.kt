package com.example.exercise.Lab1.baitap1

fun main() {
    // ct main
    println("Hello, world!")

    // in mot dong van ban
    println("This is the text to print!")

    // bien
    val age = 5
    val name = "Rover"

    var roll = 6
    var rolledValue: Int = 4

    // in bien co mau chuoi
    println("You are already $age!")
    println("You are already ${age} days old, $name!")

    // Ham khong co doi so
    printHello()

    // Ham tra ve gia tri: muon hien thi thi phai println
    println("Dice rolled: ${roll()}")

    // ham co doi so
    printBorder("a", 4)
}

fun printHello() {
    println("Hello Kotlin")
}

// ham co doi so
fun printBorder(border: String, timesToRepeat: Int) {
    repeat(timesToRepeat) {
        print(border)
    }
    println()
}

// ham tra ve mot gia tri
fun roll(): Int {
    return (1..6).random()
}

