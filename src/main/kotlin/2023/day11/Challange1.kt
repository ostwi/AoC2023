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

        for (row in rotatedBackMatrix) {
            println(row)
            println(row.size)
        }
    }
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

