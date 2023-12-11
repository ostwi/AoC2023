package `2023`.day10

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day10/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day10/data.txt").readLines()

    private val changingNodes = arrayOf('L', '7', 'F', 'J', '|', 'S')
    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val starts = findStartNodes(data)
        val results = mutableListOf<Pair<List<Pair<Int, Int>>, Int>>()
        for (start in starts) {
            println(start)
            results.add(findLoop(start.first, start.second[0]))
        }
        println(results.map { it.first })
        val mid = results.maxBy { it.second }!!
        val inside = findInsideTiles(mid.first)
        return inside
    }

    private fun findInsideTiles(path: List<Pair<Int, Int>>): Int {
        val board = mutableListOf<String>()
        for (i in data.indices) {
            var newLine = ""
           for (j in data[i].indices) {
               if (Pair(j, i) in path) {
                   newLine += data[i][j]
               } else {
                   newLine += '.'
               }
           }
            println(newLine)
            board.add(newLine)
        }
        var inside = 0
        board.map { println(it) }
        for (i in board.indices) {
            var insideLoop = false
            var cnt = 0
            var lastPipe = '.'
            for (j in board[i].indices) {
                if (changingNodes.contains(board[i][j])) {
                    if ((lastPipe == 'L') && (board[i][j] == '7')){
                        insideLoop = !insideLoop
                        lastPipe = board[i][j]
                    } else if (lastPipe == '7' && board[i][j] == 'L') {
//                        insideLoop = !insideLoop
                        lastPipe = board[i][j]
                    } else if (lastPipe == 'F' && board[i][j] == 'J') {
                        insideLoop = !insideLoop
                        lastPipe = board[i][j]
                    } else if (lastPipe == 'F' && board[i][j] == 'J') {
//                        insideLoop = !insideLoop
                        lastPipe = board[i][j]
                    } else if (board[i][j] == '|') {
                        insideLoop = !insideLoop
                        lastPipe = board[i][j]
                    }
                    lastPipe = board[i][j]
                } else if (insideLoop && board[i][j] == '.') {
                    cnt++
                }
            }
            inside += cnt
        }
        return inside
    }

    fun findLoop(start: Connector, next: Pair<Int, Int>): Pair<List<Pair<Int, Int>>, Int> {
        var depth = 0
        val startPosition = getStartPosition()
        var nodeToCheck = Triple(Connector.getConnector(data[next.first][next.second]), next, startPosition)
        var visited = mutableListOf<Pair<Int, Int>>()
        var path = mutableListOf<Pair<Int, Int>>()
        path.add(startPosition)
        while (true) {
            depth++
            val position = nodeToCheck.second
            val previousPosition = nodeToCheck.third
            path.add(position)
            if (!isPositionValid(position)){
                println("not a loop")
                return Pair(path, 0)
            }
            if (visited.contains(position)) {
                println("not a loop")
                return Pair(path, 0)
            }
            visited.add(position)
            var connector = goDown(
                position.first,
                position.second,
                Input(previousPosition.first, previousPosition.second))
            if (connector?.first == Connector.START) {
                println("loop found")
                return Pair(path, depth)
            }
            if (connector == null) {
                println("not a loop")
                return Pair(path, 0)
            }
            nodeToCheck = Triple(connector.first, connector.second, position)
        }
    }

    private fun isPositionValid(position: Pair<Int, Int>): Boolean {
        return position.second >= 0 && position.second < data.size &&
                position.first >= 0 && position.first < data[position.second].length
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

