package `2023`.day4

import java.io.File

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day4/4-test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day4/4.txt").readLines()

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

        val results = mutableListOf<Int>()

        for (i in winningPoints.indices) {
            var count = 0
            for (j in winningPoints[i].indices) {
                if(myPoints[i].contains(winningPoints[i][j])) {
                    if (count == 0) {
                        count++
                    } else {
                        count *= 2
                    }
                }
            }
            results.add(count)
        }
        return results.sum()
    }
}

