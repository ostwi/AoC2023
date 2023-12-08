package `2023`.day8

import java.io.File

class Challenge2 {
//        private val data: List<String> = File("src/main/resources/2023/Day8/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day8/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Long {
        val instructions = data[0].chars().toArray().map { it.toChar() }
        val positions = data.subList(2, data.size).map { it.getNode() }
        val positionsMap = positions.associateBy { it.position }
        var lastPositions = positions.map { it.position }.filter { it.endsWith("A") }

        val steps = lastPositions
            .map {
                getLast(instructions, positionsMap, it)
            }.map { it.toLong() }

        return steps.reduce { total, next -> lcm(total, next) }
    }

    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }


    fun String.getNode(): Node {
        return Node(
            this.take(3),
            this.substring(7, 10),
            this.substring(12, 15)
        )
    }

    data class Node(
        val position: String,
        val left: String,
        val right: String
    )

    fun getLast(instructions: List<Char>, positionsMap: Map<String, Node>, it: String): Int {
        var counter = 0
        var done = false
        var res = 0
        var last = positionsMap[it]!!.position
        while (!done) {
            res++
            val position = positionsMap[last]!!
            when (instructions[counter]) {
                'L' -> {
                    last = position.left
                    if (position.left.endsWith("Z")) {
                        done = true
                    }
                }
                'R' -> {
                    last = position.right
                    if (position.right.endsWith("Z")) {
                        done = true
                    }
                }
            }
            counter++
            if (counter >= instructions.size) {
                counter = 0
            }
        }
        return res
    }
}

