package `2023`

import java.io.File

class day1 {
    val data: List<String> = File("src/main/resources/2023/1-1.txt").readLines()
    val data2: List<String> = File("src/main/resources/2023/1-2.txt").readLines()

    fun challange1() {
        println(getResult())
    }

    fun challange2() {
        println(startBackResult())
    }

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
            process(processedLine).let {
                if (it.isEmpty()) {
                    return@map processedLine
                }
                if (it.first().second < processedLine.indexOfFirst { it.isDigit() }) {
                    processedLine = processedLine.replace(it.first().first.first, it.first().first.second)
                }
                processedLine = processedLine.replace(it.last().first.first, it.last().first.second)
                if (it.first().first != it.last().first &&
                    processedLine.filter { it.isDigit() }.length == 1 ) {
                    processedLine = "${it.first().first.second}${it.last().first.second}"
                }
            }
            processedLine
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
    // working solution
    // hallelujah
    fun startBackResult(): Any = data
        .map{
            var firstNumber = ""
            var lastNumber = ""
            val lineLength = it.length

            // Forward loop to find the first number
            for (i in 0..lineLength) {
                val substring = it.substring(0, i)
                val foundNumber = getFirstNumberAsString(substring)
                if (foundNumber.isNotEmpty()) {
                    println("found first number ${foundNumber}")
                    firstNumber = foundNumber
                    break
                }
            }

            // Backward loop to find the last number
            for (i in lineLength downTo 1) {
                val substring = it.substring(i - 1, lineLength)
                val foundNumber = getFirstNumberAsString(substring)
                if (foundNumber.isNotEmpty()) {
                    println("found last number ${foundNumber}")
                    lastNumber = foundNumber
                    break
                }
            }

            println("first number is ${firstNumber}")
            println("last number is ${lastNumber}")
            val combinedString = "${firstNumber}${lastNumber}"

            combinedString.toIntOrNull() ?: 0
        }
        .sum()

    fun getFirstNumberAsString(input: String): String {
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

        // Check for single digit first
        input.forEach { char ->
            if (char.isDigit()) {
                return char.toString()
            }
        }

        // If no single digit is found, look for number words
        for (word in numberWords.keys) {
            if (input.contains(word)) {
                return numberWords[word]!!
            }
        }

        // Return empty string if no number found
        return ""
    }

    fun main() {
        val inputStrings = listOf("ioiasdf2", "oiutwo", "seveneightnine")

        inputStrings.forEach {
            println(getFirstNumberAsString(it))
        }
    }

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
        .let { listOf(it.first(), it.last()) }
        .map { it.first }
}

fun process(text: String): List<Pair<Pair<String, String>, Int>> {
    val digitsAsText = listOf(
        Pair("one", "1"),
        Pair("two", "2"),
        Pair("three", "3"),
        Pair("four", "4"),
        Pair("five", "5"),
        Pair("six", "6"),
        Pair("seven", "7"),
        Pair("eight", "8"),
        Pair("nine", "9")
    )
    val x = digitsAsText.flatMap {
        listOf(
            Pair(it, text.indexOf(it.first)),
            Pair(it, i(text, it))
        )
    }.filter {
        it.second != -1
    }
        val t = x.distinct()
        val n = t.sortedBy { it.second }
        val m = n.let {
            if (it.isEmpty()) {
                return listOf()
            }
            listOf(it.first(), it.last())
        }
    return m
}

private fun i(text: String, it: Pair<String, String>): Int {
    val index = text.reversed().indexOf(it.first.reversed())
    if(index < 0) {
        return index
    } else {
        return text.length - index
    }
}

