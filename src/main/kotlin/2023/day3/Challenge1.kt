package `2023`.day3

import java.io.File

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day3/3-test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day3/3.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        return getNumbers(data)
            .filter { it.isAdjacentToSymbol(data) }
            .sumOf { it.number }
    }
}

