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

    fun getNumberForString(input: String): String {
        val numberWords = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        var output = input
        var foundNumberWord: Boolean

        do {
            foundNumberWord = false
            var firstNumberWord: String? = null
            var firstIndex = output.length

            for (word in numberWords.keys) {
                val index = output.indexOf(word)
                if (index in 0 until firstIndex) {
                    firstIndex = index
                    firstNumberWord = word
                    foundNumberWord = true
                }
            }

            if (firstNumberWord != null) {
                output = output.replaceFirst(firstNumberWord, numberWords[firstNumberWord]!!)
            }
        } while (foundNumberWord)

        return output
    }

    fun splitString(input: String): List<String> {
        val regex = "(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)".toRegex()
        return input.split(regex)
    }

    fun getResult2(): Int = data2
        .map{
            // divide string into list of strings divided by numbers
            val splitString = splitString(it)
            val numberForStringData = splitString.map {
                getNumberForString(it)
            }

            numberForStringData.joinToString(separator = "")
        }
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
}
