package `2023`.day2

import java.io.File

class Challenge1 {
    private val testData: List<String> = File("src/main/resources/2023/Day2/2-test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day2/2-1.txt").readLines()

    fun run() {
        println(getResult(14, 12, 13))
    }

    private fun getResult(blueLimit: Int, redLimit: Int, greenLimit: Int): Int {
        val games = data.map { parseGame(it) }
        val possibleGames = games.filter { it.isGamePossible(blueLimit, redLimit, greenLimit) }
        return possibleGames.sumOf { it.gameId }
    }

}
