package `2023`.day3

import java.io.File

class Challenge2 {
//    private val data: List<String> = File("src/main/resources/2023/Day3/3-test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day3/3.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val numbers = getNumbers(data)
        val gears = getGears(data)
        return gears.sumOf { gear ->
            numbers.filter { it.isAdjacentToGear(gear) }
                .let {
                    if (it.size == 2) {
                        it.first().number * it.last().number
                    } else {
                        0
                    }
                }
        }
    }
}

