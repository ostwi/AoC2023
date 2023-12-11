package `2023`.day11

import java.io.File

class Challenge1 {
    //    private val data: List<String> = File("src/main/resources/2023/Day9/test.txt").readLines()
    // private val data: List<String> = File("src/main/resources/2023/Day11/adam-1.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day11/adam-test.txt").readLines()

    fun run() {
        val matrix = createMatrix(data).toMutableList()
        val emptyRowMatrixExpansion = expandMatrixOnEmptyRows(matrix)
        val rotatedMatrix = rotateMatrixForth(emptyRowMatrixExpansion)
        val emptyColumnMatrixExpansion2 = expandMatrixOnEmptyRows(rotatedMatrix)
        val rotatedBackMatrix = rotateMatrixBack(emptyColumnMatrixExpansion2)
        val transformedHashtagsToNumbers = transformHashtagsToNumbers(rotatedBackMatrix)

        for (row in transformedHashtagsToNumbers) {
            println(row)
        }
    }
}

sealed class MatrixPoint {}
class Space: MatrixPoint()
data class Number(val value: Int): MatrixPoint()
object Hashtag: MatrixPoint() {
    var iterator = 0
    fun toNextNumber() = Number(++iterator)
}
fun transformHashtagsToNumbers(matrix: MutableList<CharArray>): MutableList<CharArray> {
    var iterator = 0

    val hashtag: Char = '#'
    fun Char.isHashtag() = this == hashtag

    for (row in matrix) {
        row.forEachIndexed { index, character -> row[index] = if(character.isHashtag()) (++iterator).toString().first() else character }
    }

    return matrix
}

fun rotateMatrixForth(matrix: MutableList<CharArray>): MutableList<CharArray> {
    val transposedMatrix = MutableList(matrix[0].size) { CharArray(matrix.size) }
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            transposedMatrix[j][i] = matrix[i][j]
        }
    }
    return transposedMatrix.apply { forEach { it.reverse() } }
}

fun rotateMatrixBack(matrix: MutableList<CharArray>): MutableList<CharArray> {
    val transposedMatrix = MutableList(matrix[0].size) { CharArray(matrix.size) }
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            transposedMatrix[j][i] = matrix[i][j]
        }
    }
    return transposedMatrix.apply { reverse() }
}

fun expandMatrixOnEmptyRows(matrix: MutableList<CharArray>): MutableList<CharArray> {
    val emptyChar: Char = '.'

    var i = 0
    while (i < matrix.size) {
        val row = matrix[i]
        if (row.all { it == emptyChar }) {
            val emptyRow = CharArray(row.size) { emptyChar }
            matrix.add(i, emptyRow)
            i++
        }
        i++
    }

    return matrix
}

fun createMatrix(data: List<String>): Array<CharArray> {
    val matrix = data.map { it.toCharArray() }.toTypedArray()

    return matrix
}

