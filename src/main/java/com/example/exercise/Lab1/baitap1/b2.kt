package com.example.exercise.Lab1.baitap1

//lap lai mot thao tac bang repeat()
fun printBorder() {
    repeat(23) {
    }
    println()
}

//long ghep vong lap repeat()
fun printCakeBottom(age: Int, layers: Int) {
    repeat(layers) {
        repeat(age + 2) {
            print("@")
        }
        println()
    }
}

class DiceSimple {
    var sides = 6

    fun roll() {
        val randomNumber = (1..sides).random()
        println("DiceSimple rolled: $randomNumber")
    }
}

// 7) Class Dice co tham so
class Dice(val numSides: Int) {
    fun roll(): Int {
        val randomNumber = (1..numSides).random()
        return randomNumber
    }
}

fun main() {

    println(" repeat() - printBorder ")
    printBorder()
    println()

    println(" Nested repeat - printCakeBottom ")
    printCakeBottom(age = 5, layers = 3)
    println()


    println(" if/else ")
    val num = 4
    if (num > 4) {
        println("The variable is greater than 4")
    } else if (num == 4) {
        println("The variable is equal to 4")
    } else {
        println("The variable is less than 4")
    }
    println()

    println( " when ")
    val luckyNumber = 3
    val rollResult = (1..6).random()
    println("rollResult = $rollResult")

    when (rollResult) {
        luckyNumber -> println("You won!")
        1 -> println("So sorry! You rolled a 1. Try again!")
        2 -> println("Sadly, you rolled a 2. Try again!")
        3 -> println("Unfortunately, you rolled a 3. Try again!")
        4 -> println("No luck! You rolled a 4. Try again!")
        5 -> println("Don't cry! You rolled a 5. Try again!")
        6 -> println("Apologies! you rolled a 6. Try again!")
    }
    println()

    println("when assign to variable ")
    val diceRoll = (1..6).random()
    val drawableResource = when (diceRoll) {
        1 -> "dice_1"
        2 -> "dice_2"
        3 -> "dice_3"
        4 -> "dice_4"
        5 -> "dice_5"
        else -> "dice_6"
    }
    println("diceRoll = $diceRoll -> drawableResource = $drawableResource")
    println()

    println(" Class Dice simple ")
    val dice1 = DiceSimple()
    dice1.sides = 6
    dice1.roll() // ham tu in ra
    println()

    println(" Class Dice param + create instance ")
    val myFirstDice = Dice(6)     // tao thuc the
    val result = myFirstDice.roll()
    println("myFirstDice rolled: $result")
}

