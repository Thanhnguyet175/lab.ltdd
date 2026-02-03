package com.example.exercise.Lab1.baitap2

import kotlin.math.PI

fun main() {

    println("Bai tap 2 ")

//LOP TRUU TUONG
    // Tao doi tuong tu cac lop con
    val squareCabin = SquareCabin(residents = 6, length = 10.0)
    val roundHut = RoundHut(residents = 3, radius = 5.0)
    val roundTower = RoundTower(residents = 4, radius = 4.0, floors = 2)

    // Su dung with de truy cap thuoc tinh va ham gon hon
    println("\n SquareCabin ")
    with(squareCabin) {
        println("Vat lieu: $buildingMaterial")
        println("Dien tich san: ${floorArea()}")
        println("Con cho khong? ${hasRoom()}")
    }

    println("\n RoundHut ")
    with(roundHut) {
        println("Vat lieu: $buildingMaterial")
        println("Dien tich san: ${floorArea()}")
        println("Con cho khong? ${hasRoom()}")
    }

    println("\n RoundTower ")
    with(roundTower) {
        println("Vat lieu: $buildingMaterial")
        println("Dien tich san: ${floorArea()}")
        println("Con cho khong? ${hasRoom()}")
    }

    // 2. DANH SACH (LIST)

    println("\n LIST ")
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    println("So phan tu: ${numbers.size}")
    println("Phan tu dau tien: ${numbers[0]}")
    println("Dao nguoc danh sach mau: ${listOf("red", "blue", "green").reversed()}")

    // Danh sach co the thay doi
    val entrees = mutableListOf<String>()
    entrees.add("spaghetti")
    entrees[0] = "lasagna"
    entrees.remove("lasagna")
    println("Danh sach mon an: $entrees")

    // 3. VONG LAP for

    println("\n VONG LAP for ")
    for (element in numbers) {
        print("$element ")
    }
    println()

    // 4. VONG LAP while

    println("\n VONG LAP while ")
    var index = 0
    while (index < numbers.size) {
        print("${numbers[index]} ")
        index++
    }
    println()

    // 5. CHUOI (STRING)

    println("\n--- STRING ---")
    val name = "Android"
    println("Do dai chuoi '$name' = ${name.length}")

    val number = 10
    val groups = 5
    println("$number people")
    println("${number * groups} people")

    // 6. PI (THU VIEN TOAN)

    val radius = 3.0
    println("\nDien tich hinh tron = ${PI * radius * radius}")


    // 7. vararg

    println("\n VARARG ")
    addToppings("cheese", "pepperoni", "olives")
}

/*
 * Ham co so luong tham so khong co dinh (vararg)
 */
fun addToppings(vararg toppings: String) {
    println("Topping da chon:")
    for (t in toppings) {
        println("- $t")
    }
}


// CAC LOP TRONG UNIT 2

// Lop truu tuong
abstract class Dwelling(private var residents: Int) {

    // Thuoc tinh truu tuong
    abstract val buildingMaterial: String

    // Ham truu tuong
    abstract fun floorArea(): Double

    // Ham truu tuong de tinh suc chua
    abstract fun capacity(): Int

    // Ham thuong
    fun hasRoom(): Boolean {
        return residents < capacity()
    }
}

// Lop con ke thua Dwelling
class SquareCabin(residents: Int, private val length: Double) : Dwelling(residents) {

    override val buildingMaterial: String = "Go"

    override fun floorArea(): Double {
        return length * length
    }

    override fun capacity(): Int {
        return 6
    }
}

// Lop open de cho phep ke thua
open class RoundHut(residents: Int, protected val radius: Double) : Dwelling(residents) {

    override val buildingMaterial: String = "Rom"

    override fun floorArea(): Double {
        return PI * radius * radius
    }

    override fun capacity(): Int {
        return 4
    }
}

// Lop con goi super.floorArea()
class RoundTower(residents: Int, radius: Double, private val floors: Int)
    : RoundHut(residents, radius) {

    override val buildingMaterial: String = "Da"

    override fun floorArea(): Double {
        return super.floorArea() * floors
    }

    override fun capacity(): Int {
        return 8
    }
}
