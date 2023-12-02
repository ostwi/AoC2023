package `2023`.day2

import java.io.File

class Challenge2 {
    val testData: List<String> = File("src/main/resources/2023/Day2/2-test.txt").readLines()
    val data: List<String> = File("src/main/resources/2023/Day2/2.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val games = data.map { parseGame(it) }
        val minimumGameCubesAmount = games.map { game ->
            MinimumCubeGroup(
                red = game.rounds.maxOf { it.red },
                green = game.rounds.maxOf { it.green },
                blue = game.rounds.maxOf { it.blue },
            )
        }
        return minimumGameCubesAmount.sumOf { it.red * it.green * it.blue }
    }

}
