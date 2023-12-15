package `2023`.day15

import java.io.File
import java.lang.RuntimeException
import kotlin.streams.toList

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day15/test.txt").readLines().first().split(",")
    private val data: List<String> = File("src/main/resources/2023/Day15/data.txt").readLines().first().split(",")

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        println(data)
        return data.map {it.chars().toList()}.sumOf { c -> calc(c)}
    }

    fun calc(input: List<Int>): Int {
        var res = 0
        for (i in input) {
            res = res + i
            res = res * 17
            res = res % 256
        }
        return res
    }
}

