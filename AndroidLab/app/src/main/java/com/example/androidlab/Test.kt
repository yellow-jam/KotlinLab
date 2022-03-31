package com.example.androidlab

// var data2

fun main() {
    println("hello world")
    // var data3
    var data: Any = 0
    when (data) {
        is String -> println("data is String")
        20, 30 -> println("data is 20 or 30")
        in 1..10 -> println("data is 1..10")
        else -> println("data is not valid")
    }

    val some: (Int) -> Unit = {println(it)}
    some(234);
    println(some(234));



    fun some(data1: Int, data2: Int): Int {
        return data1 * data2
    }
    some(20, 10)


    val str2="""
        
    """.trimIndent()
    println()

    var array1= arrayOf<Int>(10,20,30)
    for (i in array1.indices) {
        println(array1[i])
    }

    println("출력")
    println(array1.indices)
    println(array1.withIndex())

}