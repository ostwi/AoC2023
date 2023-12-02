package `2023`.day2

import java.lang.RuntimeException

data class Game(
    val gameId: Int,
    val rounds: List<Round>
) {
    fun isGamePossible(blueLimit: Int, redLimit: Int, greenLimit: Int): Boolean {
        this.rounds.map {
            val isPossible = it.blue <= blueLimit && it.red <= redLimit && it.green <= greenLimit
            if (!isPossible) return false
        }
        return true
    }
}

data class Round(
    val red: Int,
    val green: Int,
    val blue: Int,
)

data class CubeSet(
    val color: CubeColor,
    val amount: Int,
)

enum class CubeColor {
    GREEN,
    BLUE,
    RED,
}

data class MinimumCubeGroup(
    val red: Int,
    val green: Int,
    val blue: Int,
)

fun parseGame(game: String): Game {
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
