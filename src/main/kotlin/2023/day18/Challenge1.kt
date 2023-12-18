package `2023`.day18

import kotlin.math.*
import java.io.File

class Challenge1 {
    private val data: List<String> = File("src/main/resources/2023/Day18/test.txt").readLines()

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

        input.forEach { cmd ->
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

            printLagoon(minX, minY, maxX, maxY, lagoonEdges, emptySet())
        }

        val interior = mutableSetOf<Pair<Int, Int>>()
        for (i in minX..maxX) {
            for (j in minY..maxY) {
                if (Pair(i, j) !in lagoonEdges && isEnclosed(i, j, lagoonEdges, minX, maxX, minY, maxY)) {
                    interior.add(Pair(i, j))
                }
            }
        }

        printLagoon(minX, minY, maxX, maxY, lagoonEdges, interior)
        return interior.size + lagoonEdges.size
    }

    private fun printLagoon(minX: Int, minY: Int, maxX: Int, maxY: Int, edges: Set<Pair<Int, Int>>, interior: Set<Pair<Int, Int>>) {
        println("Current state of the lagoon:")
        for (j in minY..maxY) {
            for (i in minX..maxX) {
                when {
                    Pair(i, j) in edges -> print("#")
                    Pair(i, j) in interior -> print("#")
                    else -> print(".")
                }
            }
            println()
        }
        println()
    }

    private fun isEnclosed(x: Int, y: Int, edges: Set<Pair<Int, Int>>, minX: Int, maxX: Int, minY: Int, maxY: Int): Boolean {
        var leftBlocked = (x - 1 downTo minX).any { Pair(it, y) in edges }
        var rightBlocked = (x + 1..maxX).any { Pair(it, y) in edges }
        var upBlocked = (y - 1 downTo minY).any { Pair(x, it) in edges }
        var downBlocked = (y + 1..maxY).any { Pair(x, it) in edges }

        return leftBlocked && rightBlocked && upBlocked && downBlocked
    }
}

