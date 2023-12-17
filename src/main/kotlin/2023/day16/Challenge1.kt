package `2023`.day16

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
    //    private val data: List<String> = File("src/main/resources/2023/Day16/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day16/data.txt").readLines()

    val matrix = data.getMatrix()

    fun run() {
        println(getResult())
    }

    fun getResult(): Int {
        val path = getPath()
        val visited = mutableSetOf<Point>()
        var done = false
        var next = mutableListOf<Point>()
        next.add(Point(0, 0, matrix[0][0], Direction.EAST))
        while (!done) {
            val current  = next
            next = current.flatMap { it.move() }.toMutableList()
            current.map { markVisited(path, it) }
            current.map { visited.add(it) }
            if (visited.containsAll(next)) {
                done = true
            }
        }
        return path.sumOf { x -> x.count { it == '#' }}
    }

    fun markVisited(path: Array<CharArray>, current: Point) {
        try {
            path[current.y][current.x] = '#'
        } catch (e: ArrayIndexOutOfBoundsException) {
            println("Out of bounds: $current")
        }
    }

    private fun getPath(): Array<CharArray> {
        val path = Array(matrix.size) { CharArray(matrix[0].size) }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                path[i][j] = '.'
            }
        }
        return path
    }

    fun List<String>.getMatrix(): Array<CharArray> {
        val matrix = Array(this.size) { CharArray(this[0].length) }
        for (i in this.indices) {
            for (j in this[i].indices) {
                matrix[i][j] = this[i][j]
            }
        }
        return matrix
    }

    private fun Point.move(): List<Point> {
        val t = when (Pair(this.direction, this.value)) {
            Pair(Direction.NORTH, '|') -> listOf(Point(this.x, this.y - 1, getChar(x, y-1), this.direction)).filter { it.value != 'Z' }
            Pair(Direction.NORTH, '\\') -> listOf(Point(this.x-1, this.y, getChar(x-1, y), Direction.WEST)).filter { it.value != 'Z' }
            Pair(Direction.NORTH, '/') -> listOf(Point(this.x + 1, this.y, getChar(x+1, y), Direction.EAST)).filter { it.value != 'Z' }
            Pair(Direction.NORTH, '.') -> listOf(Point(this.x, this.y-1, getChar(x, y-1), this.direction)).filter { it.value != 'Z' }
            Pair(Direction.NORTH, '-') -> listOf(
                Point(this.x - 1, this.y, getChar(x-1, y), Direction.WEST),
                Point(this.x + 1, this.y, getChar(x+1, y), Direction.EAST)
            ).filter { it.value != 'Z' }

            Pair(Direction.WEST, '|') -> listOf(
                Point(this.x, this.y - 1, getChar(x, y-1), Direction.NORTH),
                Point(this.x, this.y + 1, getChar(x, y+1), Direction.SOUTH),
            ).filter { it.value != 'Z' }
            Pair(Direction.WEST, '\\') -> listOf(Point(this.x, this.y - 1, getChar(x, y-1), Direction.NORTH)).filter { it.value != 'Z' }
            Pair(Direction.WEST, '/') -> listOf(Point(this.x, this.y + 1, getChar(x, y+1), Direction.SOUTH)).filter { it.value != 'Z' }
            Pair(Direction.WEST, '.') -> listOf(Point(this.x - 1, this.y, getChar(x-1, y), this.direction)).filter { it.value != 'Z' }
            Pair(Direction.WEST, '-') -> listOf(Point(this.x - 1, this.y, getChar(x-1, y), this.direction)).filter { it.value != 'Z' }

            Pair(Direction.SOUTH, '|') -> listOf(Point(this.x, this.y + 1, getChar(x, y+1), this.direction)).filter { it.value != 'Z' }
            Pair(Direction.SOUTH, '\\') -> listOf(Point(this.x+1, this.y, getChar(x+1, y), Direction.EAST)).filter { it.value != 'Z' }
            Pair(Direction.SOUTH, '/') -> listOf(Point(this.x - 1, this.y, getChar(x-1, y), Direction.WEST)).filter { it.value != 'Z' }
            Pair(Direction.SOUTH, '.') -> listOf(Point(this.x, this.y+1, getChar(x, y+1), this.direction)).filter { it.value != 'Z' }
            Pair(Direction.SOUTH, '-') -> listOf(
                Point(this.x - 1, this.y, getChar(x-1, y), Direction.WEST),
                Point(this.x+1, this.y, getChar(x+1, y), Direction.EAST),
            ).filter { it.value != 'Z' }

            Pair(Direction.EAST, '|') -> listOf(
                Point(this.x, this.y - 1, getChar(x, y-1), Direction.NORTH),
                Point(this.x, this.y + 1, getChar(x, y+1), Direction.SOUTH),
            ).filter { it.value != 'Z' }
            Pair(Direction.EAST, '\\') -> listOf(Point(this.x, this.y + 1, getChar(x, y+1), Direction.SOUTH)).filter { it.value != 'Z' }
            Pair(Direction.EAST, '/') -> listOf(Point(this.x, this.y - 1, getChar(x, y-1), Direction.NORTH)).filter { it.value != 'Z' }
            Pair(Direction.EAST, '.') -> listOf(Point(this.x + 1, this.y, getChar(x+1, y), this.direction)).filter { it.value != 'Z' }
            Pair(Direction.EAST, '-') -> listOf(Point(this.x + 1, this.y, getChar(x+1, y), this.direction)).filter { it.value != 'Z' }
            else -> throw RuntimeException("Invalid point: $this")
        }
        return t
    }

    fun getChar(x: Int, y: Int): Char {
        return try {
            matrix[y][x]
        } catch (e: ArrayIndexOutOfBoundsException) {
            'Z'
        }
    }

    data class Point(val x: Int, val y: Int, val value: Char, val direction: Direction)

    enum class Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
