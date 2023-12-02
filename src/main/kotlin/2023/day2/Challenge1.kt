package `2023`.day2

import java.io.File

class Challenge1 {
    val testData: List<String> = File("src/main/resources/2023/Day2/2-1-test.txt").readLines()
    val data: List<String> = File("src/main/resources/2023/Day2/2-1.txt").readLines()

    fun run() {
        println(getResult(14, 12, 13))
    }

    private fun getResult(blueLimit: Int, redLimit: Int, greenLimit: Int): Int {
        val games = data.map { parseGame(it) }
        val possibleGames = games.filter { it.isGamePossible(blueLimit, redLimit, greenLimit) }
        return possibleGames.map { it.gameId }.sum()
    }

}
