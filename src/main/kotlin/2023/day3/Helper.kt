package `2023`.day3

import kotlin.math.max
import kotlin.math.min

fun getNumbers(data: List<String>): List<NumberWithPosition> {
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

fun getGears(data: List<String>): List<GearWithPosition> {
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

fun NumberWithPosition.isAdjacentToSymbol(data: List<String>): Boolean {
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

fun NumberWithPosition.isAdjacentToGear(gear: GearWithPosition): Boolean {
    val isAdjacentAbove = (this.y - 1) == gear.y
            && IntRange(this.xStart - 1, this.xEnd + 1).contains(gear.x)

    val isAdjacentBelow = (this.y + 1) == gear.y
            && IntRange(this.xStart - 1, this.xEnd + 1).contains(gear.x)

    val isAdjacentOnTheSide = this.y == gear.y
            && (this.xStart - 1 == gear.x || this.xEnd + 1 == gear.x)

    return isAdjacentAbove || isAdjacentBelow || isAdjacentOnTheSide
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
