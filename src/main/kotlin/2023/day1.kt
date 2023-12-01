package `2023`

import java.io.File
import kotlin.reflect.typeOf

class day1 {
    val data: List<String> = File("src/main/resources/2023/1-1.txt").readLines()
    val data2: List<String> = File("src/main/resources/2023/1-2.txt").readLines()

    fun challange1() {
        println(getResult())
    }

    fun challange2() {
        println(getResult2())
    }

    // filter only numbers
    // filter to only have first and last number (1 => 11, 123 => 13)
    // map to int
    // sum of all calibratedValues
    fun getResult(): Int = data
        .map{
            it.filter { letter -> letter.isDigit() }
        }
        .map{
            if (
                it.length == 1
            ) {
                "${it.first()}${it.first()}"
            } else {
                "${it.first()}${it.last()}"
            }
        }
        .map{
            it.toInt()
        }
        .sum()

    fun getResult2(): Int = data2
        .map{
            it.replace("one", "1")
            .replace("two", "2")
            .replace("three", "3")
            .replace("four", "4")
            .replace("five", "5")
            .replace("six", "6")
            .replace("seven", "7")
            .replace("eight", "8")
            .replace("nine", "9")
        }
        .map{
            println(it)
            it.filter { letter -> letter.isDigit() }
        }
        .map{
            println(it)
            if (
                it.length == 1
            ) {
                "${it.first()}${it.first()}"
            } else {
                "${it.first()}${it.last()}"
            }
        }
        .map{
            println(it)
            it.toInt()
        }
        .sum()
}
