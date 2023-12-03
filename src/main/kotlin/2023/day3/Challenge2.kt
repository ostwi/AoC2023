package `2023`.day3

import java.io.File
import kotlin.math.max
import kotlin.math.min

class Challenge2 {
//    private val data: List<String> = File("src/main/resources/2023/Day3/3-test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day3/3.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val numbers = getNumbers()
        val gears = getGears()
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

    private fun NumberWithPosition.isAdjacentToGear(gear: GearWithPosition): Boolean {
        val isAdjacentAbove = (this.y - 1) == gear.y
                && IntRange(this.xStart - 1, this.xEnd + 1).contains(gear.x)

        val isAdjacentBelow = (this.y + 1) == gear.y
                && IntRange(this.xStart - 1, this.xEnd + 1).contains(gear.x)

        val isAdjacentOnTheSide = this.y == gear.y
                && (this.xStart - 1 == gear.x || this.xEnd + 1 == gear.x)

        return isAdjacentAbove || isAdjacentBelow || isAdjacentOnTheSide
    }

    private fun getGears(): List<GearWithPosition> {
        val gears = mutableListOf<GearWithPosition>()
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (data[i][j] == '*') {
                    gears.add(
                        GearWithPosition(
                            x = j,
                            y = i
                        )
                    )
                }
            }
        }
        return gears
    }

    private fun getNumbers(): List<NumberWithPosition> {
        val numbers = mutableListOf<NumberWithPosition>()
        var x: Int = 0
        var y: Int = 0
        var numberString: String = ""
        for (i in data.indices) {
            for (j in 0..< data[i].length) {
                if (data[i][j].isDigit()) {
                    x = j
                    y = i
                    numberString += data[i][j]
                } else if (numberString.isNotEmpty()){
                    numbers.add(
                        NumberWithPosition(
                            number = numberString.toInt(),
                            xStart = x - numberString.length + 1,
                            xEnd = x,
                            y = y,
                        )
                    )
                    x = 0
                    y = 0
                    numberString = ""
                }
            }
            if (numberString.isNotEmpty()) {
                numbers.add(
                    NumberWithPosition(
                        number = numberString.toInt(),
                        xStart = x - numberString.length + 1,
                        xEnd = x,
                        y = y,
                    )
                )
                x = 0
                y = 0
                numberString = ""
            }
        }
        return numbers
    }

    data class NumberWithPosition(
        val number: Int,
        val xStart: Int,
        val xEnd: Int,
        val y: Int,
    )

    data class GearWithPosition(
        val x: Int,
        val y: Int,
    )

}

