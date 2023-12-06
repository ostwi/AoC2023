package `2023`.day6

data class Race(
    val time: Int,
    val distance: Int,
)

fun parseStringToIntList(input: String): List<Int> {
    return input
        .substringAfter(": ")
        .trim()
        .split(" ")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
}

fun getRaces(size: Int, times: List<Int>, distances: List<Int>): List<Race> {
    val races = mutableListOf<Race>()
    for (i in 0..< size) {
        races.add(Race(times[i], distances[i]))
    }
    return races
}

fun parseBigRace(input: String): Long =
    input
        .substringAfter(": ")
        .trim()
        .split(" ")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .joinToString { it }
        .toLong()

fun getRecordSolutions(race: Race): Int {
    var recordSolutions = 0
    for (i in 0..<race.time) {
        val distance = calculateDistance(race.time, i)
        val solution = Solution(i, distance)
        if (solution.distance > race.distance) {
            recordSolutions++
        }
    }
    return recordSolutions
}

fun calculateDistance(givenTime: Int, holdingTime: Int): Int {
    return (givenTime - holdingTime) * holdingTime
}

data class Solution(
    val holdingTime: Int,
    val distance: Int,
)

