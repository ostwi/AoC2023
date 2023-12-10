package `2023`.day10

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
    private val data: List<String> = File("src/main/resources/2023/Day10/test.txt").readLines()
//    private val data: List<String> = File("src/main/resources/2023/Day10/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val starts = findStartNodes(data)
        for (start in starts) {
            println(start)
            println( findLoop(start.first, start.second[0], start.second[1]))
        }
        return 0
    }
    fun findLoop(start: Connector, left: Pair<Int, Int>, right: Pair<Int, Int>): Int {
        var depth = 0
        var nodesToCheck = mutableListOf(
            Pair(left,getStartPosition()),
            Pair(right,getStartPosition())
        )
        var visited = mutableListOf<Pair<Int, Int>>()
        var state = State.LOOP
        while (state == State.LOOP) {
            depth++
            val newNodesToCheck = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            for (nodeSet in nodesToCheck) {
                println(nodeSet)
                val position = nodeSet.first
                val previousPosition = nodeSet.second
                if (!isPositionValid(position)){
                    continue
                }
                if (visited.contains(position)) {
                    return 0
                }
                visited.add(position)
                var connector = goDown(
                    position.first,
                    position.second,
                    Input(previousPosition.first, previousPosition.second))
                if (connector?.first == Connector.START) {
                    state = State.START
                    break
                }
                if (connector != null) {
                    newNodesToCheck.add(Pair(connector.second, position))
                }
            }
            if (newNodesToCheck.isEmpty()) {
                return 0
            }
            nodesToCheck = newNodesToCheck
        }
        return depth
    }

    private fun isPositionValid(position: Pair<Int, Int>): Boolean {
        return position.first >= 0 && position.first < data.size &&
                position.second >= 0 && position.second < data[position.first].length
    }

    enum class State {
        START,
        LOOP,
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
            println(Connector.getConnector(data[y][x]))
            println(getPossibleStartPositions(Connector.getConnector(data[y][x]), x, y))
            println("input: $input")
            val nextPosition = getPossibleStartPositions(Connector.getConnector(data[y][x]), x, y)
                .filter { it != Pair(input.x, input.y) }
                .getOrNull(0) ?: return null
            println(nextPosition)
            Pair(Connector.getConnector(data[x][y]), nextPosition)
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
                return Pair(i, data[i].indexOf("S"))
            }
        }
        throw RuntimeException("No start position found")
    }

    fun findStartNodes(data: List<String>): List<Pair<Connector, List<Pair<Int, Int>>>> {
        var x = 0
        var y = 0
        for (i in data.indices) {
            if (data[i].indexOf("S") != -1) {
                x = i
                y = data[i].indexOf("S")
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
                return listOf(Pair(x + 1, y), Pair(x, y + 1))
            }
            Connector.RIGHT_DOWN -> {
                return listOf(Pair(x - 1, y), Pair(x, y + 1))
            }
            Connector.LEFT_UP -> {
                return listOf(Pair(x + 1, y), Pair(x, y - 1))
            }
            Connector.RIGHT_UP -> {
                return listOf(Pair(x - 1, y), Pair(x, y - 1))
            }
            Connector.GROUND -> {
                return listOf(Pair(x, y - 1))
            }
            Connector.START -> {
                return listOf(Pair(x, y + 1))
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

