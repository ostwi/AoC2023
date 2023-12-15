package `2023`.day14

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day14/test.txt").readLines().filter { it.isNotBlank() }
    private val data: List<String> = File("src/main/resources/2023/Day14/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val cols = getColumns()
        val x = cols.map { getPressure(it) }
            .sumOf { pair -> pair.sumOf{calc(it) }}
        return x
    }

    private fun calc(pair: Pair<Int, Int>): Int {
        var sum = 0
        for (i in 0..<pair.second){
            sum += pair.first - i -1
        }
        return sum
    }

    private fun getPressure(it: List<Char>): MutableList<Pair<Int, Int>> {
        val pressure = mutableListOf<Pair<Int,Int>>()
        var pres = 0
        var pos = 0
        for (i in it.indices) {
            if (it[i] == '#') {
                if (pres > 0) {
                    pressure.add(Pair(it.size-pos, pres))
                }
                pos = i
                pres = 0
            }
            if (it[i] == 'O') {
                pres++
            }
        }
        if (pres > 0) {
            pressure.add(Pair(it.size-pos, pres))
        }
        println(pressure)
        return pressure
    }

    private fun getColumns(): MutableList<List<Char>> {
        val cols = mutableListOf<List<Char>>()
        for (i in data[0].indices) {
            val col = mutableListOf<Char>()
            col.add('#')
            for (line in data) {
                col.add(line[i])
            }
            cols.add(col)
        }
        for (col in cols) {
            println(col)
        }
        return cols
    }
}

