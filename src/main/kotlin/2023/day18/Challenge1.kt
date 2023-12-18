package `2023`.day18

import kotlin.math.*
import java.io.File

class Challenge1 {
    private val data: List<String> = File("src/main/resources/2023/Day18/adam-1.txt").readLines()

    fun run() {
        val lagoonArea = getResult(data)
        println("Cubic meters of lava the lagoon can hold: $lagoonArea")
    }

    private fun getResult(input: List<String>): Int {
        val lagoonEdges = HashSet<Pair<Int, Int>>()
        var x = 0
        var y = 0
        var minX = 0
        var maxX = 0
        var minY = 0
        var maxY = 0

        for (cmd in input) {
            val parts = cmd.split(" ")
            val direction = parts[0][0]
            val steps = parts[1].split("(")[0].toInt()

            repeat(steps) {
                when (direction) {
                    'U' -> y--
                    'D' -> y++
                    'L' -> x--
                    'R' -> x++
                }
                lagoonEdges.add(Pair(x, y))
                minX = minOf(minX, x)
                maxX = maxOf(maxX, x)
                minY = minOf(minY, y)
                maxY = maxOf(maxY, y)
            }
        }

        var interiorCount = 0
        for (i in minX..maxX) {
            for (j in minY..maxY) {
                if (Pair(i, j) !in lagoonEdges && isEnclosed(i, j, lagoonEdges, minX, maxX, minY, maxY)) {
                    interiorCount++
                }
            }
        }

        return interiorCount + lagoonEdges.size
    }

    private fun isEnclosed(x: Int, y: Int, edges: Set<Pair<Int, Int>>, minX: Int, maxX: Int, minY: Int, maxY: Int): Boolean {
        var leftBlocked = (x - 1 downTo minX).any { Pair(it, y) in edges }
        var rightBlocked = (x + 1..maxX).any { Pair(it, y) in edges }
        var upBlocked = (y - 1 downTo minY).any { Pair(x, it) in edges }
        var downBlocked = (y + 1..maxY).any { Pair(x, it) in edges }

        return leftBlocked && rightBlocked && upBlocked && downBlocked
    }
}

