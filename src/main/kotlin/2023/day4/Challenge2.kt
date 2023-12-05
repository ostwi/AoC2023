package `2023`.day4

import java.io.File

class Challenge2 {
    private val data: List<String> = File("src/main/resources/2023/Day4/4-test.txt").readLines()
//    private val data: List<String> = File("src/main/resources/2023/Day4/4.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val winningPoints = data.map { line ->
            line.substringAfter(": ")
                .substringBefore("|")
                .trim()
                .split(" ")
                .filter { it != "" }
                .map { it.toInt() }
        }
        val myPoints = data.map { line ->
            line.substringAfter("| ")
                .trim()
                .split(" ")
                .filter { it != "" }
                .map { it.toInt() }
        }

        val counts = mutableListOf<Int>()

        for (i in winningPoints.indices) {
            var count = 0
            for (j in winningPoints[i].indices) {
                if(myPoints[i].contains(winningPoints[i][j])) {
                    count++
                }
            }
            counts.add(count)
        }

        return getScore(counts)
    }

    private fun getScore(results: List<Int>): Int {
        val copies = mutableListOf<Int>()
        repeat(results.size) { copies.add(1) }
        for (i in results.indices) {
            for (k in 1..copies[i]) {
                for (j in 1..results[i]) {
                    copies[i+j]++
                }
            }
        }
        return copies.sum()
    }
}

