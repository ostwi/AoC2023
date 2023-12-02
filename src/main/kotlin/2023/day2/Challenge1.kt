package `2023`.day2

import java.io.File
import java.lang.RuntimeException

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

    private fun parseGame(game: String): Game {
        val roundId = game.substringAfter("Game ").substringBefore(":").toInt()
        val rounds = game.substringAfter("Game $roundId: ")
            .split(";")
            .map { it.trim() }
            .map { revelation ->
                revelation.split(",")
                    .map { it.trim() }
                    .map { cubeSet ->
                        parseCubeSet(cubeSet)
                    }
            }
            .map { sets ->
                Round(
                    red = sets.filter { it.color == CubeColor.RED }.sumOf { it.amount },
                    green = sets.filter { it.color == CubeColor.GREEN }.sumOf { it.amount },
                    blue = sets.filter { it.color == CubeColor.BLUE }.sumOf { it.amount }
                )
            }

        return Game(
            gameId = roundId,
            rounds = rounds
        )
    }

    private fun parseCubeSet(input: String): CubeSet {
        val data = input.split(" ")
        val color = parseColor(data.last())
        val amount = data.first().toInt()
        return CubeSet(color, amount)
    }

    private fun parseColor(color: String): CubeColor {
        return when(color) {
            "green" -> CubeColor.GREEN
            "red" -> CubeColor.RED
            "blue" -> CubeColor.BLUE
            else -> throw RuntimeException(color)
        }
    }
}
