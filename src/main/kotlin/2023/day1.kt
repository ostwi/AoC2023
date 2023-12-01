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

    fun getResult2(): Int = data
        .map{ line ->
            var processedLine = line
            line.getReplacingOrder().map {
                processedLine = processedLine.replace(it.first, it.second.toString())
            }
            processedLine
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

fun String.getReplacingOrder(): List<Pair<String, Int>> {
    val digitsAsText = listOf(
        Pair("one", 1),
        Pair("two", 2),
        Pair("three", 3),
        Pair("four", 4),
        Pair("five", 5),
        Pair("six", 6),
        Pair("seven", 7),
        Pair("eight", 8),
        Pair("nine", 9)
    )
    return digitsAsText.map { pair ->
        Pair(pair, this.indexOf(pair.first))
    }
        .filter { it.second != -1 }
        .sortedBy { it.second }
        .map { it.first }
}
