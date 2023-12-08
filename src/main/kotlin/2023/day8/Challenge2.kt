package `2023`.day8

import java.io.File

class Challenge2 {
        private val data: List<String> = File("src/main/resources/2023/Day7/test.txt").readLines()
//    private val data: List<String> = File("src/main/resources/2023/Day7/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Long {
        val instructions = data[0].chars().toArray().map { it.toChar() }
        val positions = data.subList(2, data.size).map { it.getNode() }
        val positionsMap = positions.associateBy { it.position }
        var lastPosition = "AAA"
        var result = 0L
        var finished = false
        var counter = 0

        while (!finished) {
            result++
            val position = positionsMap[lastPosition]!!
            when (instructions[counter]) {
                'L' -> {
                    lastPosition = position.left
                    if (position.left == "ZZZ") {
                        finished = true
                    }
                }
                'R' -> {
                    lastPosition = position.right
                    if (position.right == "ZZZ") {
                        finished = true
                    }
                }
            }
            counter++
            if (counter >= instructions.size) {
                counter = 0
            }
        }
        return result
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
}

