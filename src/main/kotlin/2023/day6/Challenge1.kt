package `2023`.day6

import java.io.File

class Challenge1 {
    private val data: List<String> = File("src/main/resources/2023/Day6/test.txt").readLines()
//    private val data: List<String> = File("src/main/resources/2023/Day6/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Long {
        val times = parseStringToIntList(data[0])
        val records = parseStringToIntList(data[1])
        val races = getRaces(times.size, times, records)
        println(races)
        val possibilities = races.map {
            getRecordSolutions(it)
        }
        return possibilities.reduce(Int::times).toLong()
    }
}

