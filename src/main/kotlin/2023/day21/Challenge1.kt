package `2023`.day21

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
//        private val data: List<String> = File("src/main/resources/2023/Day21/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day21/data.txt").readLines()
    private val steps = 64

    fun run() {
        println(getResult())
    }

    fun getResult(): Long {
        val start = getStart()
        println(move(start, steps))
        return 0
    }

    private fun move(start: Pair<Int, Int>, steps: Int): Int {
        var places = setOf(start)
        for(i in 0..<steps) {
            val newPlaces = mutableSetOf<Pair<Int, Int>>()
            for (place in places) {
                place.getNextPlaces().forEach { newPlaces.add(it) }
            }
            places = newPlaces
        }
        return places.size
    }

    private fun getStart(): Pair<Int, Int> {
        for(i in data.indices) {
            for(j in data[i].indices) {
                if(data[i][j] == 'S') {
                    return Pair(i, j)
                }
            }
        }
        throw RuntimeException("No start found")
    }

    private fun Pair<Int, Int>.getNextPlaces(): Set<Pair<Int, Int>> {
        return listOfNotNull(this.getNorth(), this.getSouth(), this.getWest(), this.getEast())
            .filter { it.second != '#' }
            .map { it.first }
            .toSet()

    }

    private fun Pair<Int, Int>.getNorth(): Pair<Pair<Int, Int>, Char>? {
        return try {
            Pair(Pair(this.first, this.second - 1), data[this.second - 1][this.first])
        } catch (e: Exception) {
            null
        }
    }

    private fun Pair<Int, Int>.getSouth(): Pair<Pair<Int, Int>, Char>? {
        return try {
            Pair(Pair(this.first, this.second + 1), data[this.second + 1][this.first])
        } catch (e: Exception) {
            null
        }
    }

    private fun Pair<Int, Int>.getWest(): Pair<Pair<Int, Int>, Char>? {
        return try {
            Pair(Pair(this.first - 1, this.second), data[this.second][this.first - 1])
        } catch (e: Exception) {
            null
        }
    }

    private fun Pair<Int, Int>.getEast(): Pair<Pair<Int, Int>, Char>? {
        return try {
            Pair(Pair(this.first + 1, this.second), data[this.second][this.first + 1])
        } catch (e: Exception) {
            null
        }
    }
}

