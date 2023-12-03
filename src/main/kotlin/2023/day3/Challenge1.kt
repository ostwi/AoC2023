package `2023`.day3

import java.io.File
import kotlin.math.max
import kotlin.math.min

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day3/3-test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day3/3.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        return getNumbers()
            .filter { it.isAdjacentToSymbol() }
            .sumOf { it.number }
    }

    private fun NumberWithPosition.isAdjacentToSymbol(): Boolean {
        val hasSymbolAbove = data.getOrNull(this.y - 1)
            ?.substring(max(xStart - 1, 0), min(xEnd + 2, data[this.y].length))
            ?.filter { !it.isLetter() }
            ?.filter { !it.isDigit() }
            ?.any { it != '.' } ?: false

        val hasSymbolBelow = data.getOrNull(this.y + 1)
            ?.substring(max(xStart - 1, 0), min(xEnd + 2, data[this.y].length))
            ?.filter { !it.isLetter() }
            ?.filter { !it.isDigit() }
            ?.any { it != '.' } ?: false

        val hasSymbolLeft = data[y].getOrNull(xStart - 1)
            ?.let { !it.isLetter() && !it.isDigit() && it != '.' } ?: false

        val hasSymbolRight = data[y].getOrNull(xEnd + 1)
            ?.let { !it.isLetter() && !it.isDigit() && it != '.' } ?: false

        return hasSymbolAbove || hasSymbolBelow || hasSymbolLeft || hasSymbolRight
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

}

