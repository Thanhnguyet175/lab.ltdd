package com.example.exercise.Lab1.baitap3

fun main() {

    println(" BAI 3 ")

    // SET (nhom)
    println("\n1) SET ")
    val numbers = listOf(0, 3, 8, 4, 0, 5, 5, 8, 9, 2)
    val setOfNumbers = numbers.toSet()
    println("List -> Set (xoa trung): $setOfNumbers")

    val set1 = setOf(1, 2, 3)
    val set2 = mutableSetOf(3, 4, 5)
    println("Giao (intersect): ${set1.intersect(set2)}") // 3
    println("Hop (union): ${set1.union(set2)}")          // 1,2,3,4,5

    // 2) MAP (so do)
    println("\n 2) MAP ")
    val peopleAges = mutableMapOf(
        "Fred" to 30,
        "Ann" to 23
    )

    // Them/cap nhat gia tri trong map
    peopleAges.put("Barbara", 42)
    peopleAges["Joe"] = 51

    //  lap tung cap key-value
    print("forEach: ")
    peopleAges.forEach { print("${it.key} is ${it.value}, ") }
    println()

    // chuyen tung phan tu thanh chuoi
    val text = peopleAges.map { "${it.key} is ${it.value}" }.joinToString(", ")
    println("map + joinToString: $text")

    // loc phan tu theo dieu kien
    val filteredNames = peopleAges.filter { it.key.length < 4 }
    println("filter (ten < 4 ky tu): $filteredNames")

    // (3) xau chuoi thao tac
    println("\n 3) CHAIN OPERATIONS ")
    val words = listOf("about", "acute", "balloon", "best", "brief", "class")
    val filteredWords = words
        .filter { it.startsWith("b", ignoreCase = true) } // loc tu bat dau bang b/B
        .shuffled()                                       // tron ngau nhien
        .take(2)                                          // lay 2 tu
        .sorted()                                         // sap xep
    println("Ket qua chain: $filteredWords")

    // 4) let / apply
    println("\n--- 4) let / apply ---")

    // let: thuong dung voi bien nullable (co the null)
    val maybeId: String? = "123"
    maybeId?.let {
        println("let: ID = $it")
    }

    // apply: cau hinh doi tuong roi tra ve chinh no
    val sb = StringBuilder().apply {
        append("Xin chao ")
        append("Kotlin")
    }
    println("apply: $sb")

    // 5) THUOC TINH DU PHONG (backing property) - demo
    println("\n 5) Backing property ")
    val demoBacking = BackingPropertyDemo()
    demoBacking.setWord("test")
    println("currentWord = ${demoBacking.currentWord}")

    // 6) SAFE CALL (?.) + Elvis (?:)
    println("\n 6) Safe call + Elvis ")
    val input: String? = null
    val length = input?.length ?: 0  // neu input null -> 0
    println("length = $length")


    // 7) LAMBDA
    println("\n 7) Lambda ")
    val triple: (Int) -> Int = { a: Int -> a * 3 }
    println("triple(5) = ${triple(5)}")

    // 8) COMPANION OBJECT
    println("\n 8) Companion object ")
    println("HANG SO LETTER = ${DetailActivity.LETTER}")

    // 9) UY QUYEN THUOC TINH
    println("\n 9) Delegation (by lazy) ")
    val lazyDemo = LazyDemo()
    println("Chua goi message")
    println(lazyDemo.message) // luc nay moi khoi tao

    // 10) LATEINIT
    println("\n 10) lateinit ")
    val lateDemo = LateInitDemo()
    lateDemo.initWord("kotlin")
    lateDemo.printWord()
}

//   5) Backing property demo
class BackingPropertyDemo {
    private var _currentWord: String = "mac dinh"

    val currentWord: String
        get() = _currentWord

    fun setWord(word: String) {
        _currentWord = word
    }
}

   //8) Companion object demo
class DetailActivity {
    companion object {
        const val LETTER = "letter"
    }
}

   //9) Delegation demo (by lazy)
class LazyDemo {
    val message: String by lazy {
        "Day la message duoc tao bang lazy!"
    }
}

   //10) lateinit demo
class LateInitDemo {
    private lateinit var currentWord: String

    fun initWord(word: String) {
        currentWord = word
    }

    fun printWord() {
        println("lateinit word = $currentWord")
    }
}
