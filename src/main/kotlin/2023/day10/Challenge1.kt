package `2023`.day10

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day10/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day10/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val starts = findStartNodes(data)
        val results = mutableListOf<Int>()
        for (start in starts) {
            println(start)
            results.add(findLoop(start.first, start.second[0]) / 2)
        }
        return results.max()
    }

    fun findLoop(start: Connector, next: Pair<Int, Int>): Int {
        var depth = 0
        val startPosition = getStartPosition()
        var nodeToCheck = Triple(Connector.getConnector(data[next.first][next.second]), next, startPosition)
        var visited = mutableListOf<Pair<Int, Int>>()
        while (true) {
            depth++
            val position = nodeToCheck.second
            val previousPosition = nodeToCheck.third
            if (!isPositionValid(position)){
                println("not a loop")
                return 0
            }
            if (visited.contains(position)) {
                println("not a loop")
                return 0
            }
            visited.add(position)
            var connector = goDown(
                position.first,
                position.second,
                Input(previousPosition.first, previousPosition.second))
            if (connector?.first == Connector.START) {
                println("loop found")
                return depth
            }
            if (connector == null) {
                println("not a loop")
                return 0
            }
            nodeToCheck = Triple(connector.first, connector.second, position)
        }
    }

    private fun isPositionValid(position: Pair<Int, Int>): Boolean {
        return position.first >= 0 && position.first < data.size &&
                position.second >= 0 && position.second < data[position.first].length
    }

    data class Input(
        val x: Int,
        val y: Int,
    )

    fun goDown(x: Int, y: Int, input: Input): Pair<Connector, Pair<Int, Int>>? {
        var possibleConnectors = mutableListOf<Connector>()
        if (x == input.x && y > input.y) {
            possibleConnectors.add(Connector.VERTICAL)
            possibleConnectors.add(Connector.LEFT_UP)
            possibleConnectors.add(Connector.RIGHT_UP)
            possibleConnectors.add(Connector.START)
        }
        if (x == input.x && y < input.y) {
            possibleConnectors.add(Connector.VERTICAL)
            possibleConnectors.add(Connector.LEFT_DOWN)
            possibleConnectors.add(Connector.RIGHT_DOWN)
            possibleConnectors.add(Connector.START)
        }
        if (x > input.x && y == input.y) {
            possibleConnectors.add(Connector.HORIZONTAL)
            possibleConnectors.add(Connector.LEFT_DOWN)
            possibleConnectors.add(Connector.LEFT_UP)
            possibleConnectors.add(Connector.START)
        }
        if (x < input.x && y == input.y) {
            possibleConnectors.add(Connector.HORIZONTAL)
            possibleConnectors.add(Connector.RIGHT_DOWN)
            possibleConnectors.add(Connector.RIGHT_UP)
            possibleConnectors.add(Connector.START)
        }
        return if (possibleConnectors.contains(Connector.getConnector(data[y][x]))) {
            val nextPosition = getPossibleStartPositions(Connector.getConnector(data[y][x]), x, y)
                .filter { it != Pair(input.x, input.y) }
                .getOrNull(0) ?: return null
            Pair(Connector.getConnector(data[y][x]), nextPosition)
        } else {
            null
        }
    }

    data class TraverseResult(
        val depth: Int,
        val reachedStart: Boolean,
    )

    fun getStartPosition(): Pair<Int, Int> {
        for (i in data.indices) {
            if (data[i].indexOf("S") != -1) {
                return Pair(data[i].indexOf("S"), i)
            }
        }
        throw RuntimeException("No start position found")
    }

    fun findStartNodes(data: List<String>): List<Pair<Connector, List<Pair<Int, Int>>>> {
        var x = 0
        var y = 0
        for (i in data.indices) {
            if (data[i].indexOf("S") != -1) {
                y = i
                x = data[i].indexOf("S")
                break
            }
        }
        val starts = Connector.entries
            .filter { it != Connector.START && it != Connector.GROUND }
            .map { Pair(it,getPossibleStartPositions(it,x,y)) }
            .map {(connector, list) ->
                Pair(connector,list.filter { it.first != -1 && it.second != -1 })
            }
            .filter { it.second.size == 2 }
        return starts

    }

    fun getPossibleStartPositions(connector: Connector, x: Int, y: Int): List<Pair<Int, Int>> {
        when(connector) {
            Connector.VERTICAL -> {
                return listOf(Pair(x, y - 1), Pair(x, y + 1))
            }
            Connector.HORIZONTAL -> {
                return listOf(Pair(x - 1, y), Pair(x + 1, y))
            }
            Connector.LEFT_DOWN -> {
                return listOf(Pair(x - 1, y), Pair(x, y + 1))
            }
            Connector.RIGHT_DOWN -> {
                return listOf(Pair(x + 1, y), Pair(x, y + 1))
            }
            Connector.LEFT_UP -> {
                return listOf(Pair(x - 1, y), Pair(x, y - 1))
            }
            Connector.RIGHT_UP -> {
                return listOf(Pair(x + 1, y), Pair(x, y - 1))
            }
            Connector.GROUND -> {
                return emptyList()
            }
            Connector.START -> {
                return listOf(Pair(x, y + 1), Pair(x, y - 1), Pair(x+1, y), Pair(x-1, y))
            }
        }
    }


    data class Node(
        val x: Int,
        val y: Int,
        val value: Connector,
    )

    enum class Connector {
        VERTICAL,
        HORIZONTAL,
        LEFT_DOWN,
        RIGHT_DOWN,
        LEFT_UP,
        RIGHT_UP,
        GROUND,
        START;

        companion object {
            fun getConnector(node: Char): Connector {
                return when (node) {
                    '|' -> VERTICAL
                    '-' -> HORIZONTAL
                    '7' -> LEFT_DOWN
                    'F' -> RIGHT_DOWN
                    'J' -> LEFT_UP
                    'L' -> RIGHT_UP
                    '.' -> GROUND
                    'S' -> START
                    else -> throw RuntimeException("Unknown connector")
                }
            }
        }
    }
}

