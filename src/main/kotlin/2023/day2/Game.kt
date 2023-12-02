package `2023`.day2

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
