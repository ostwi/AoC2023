package `2023`.day5

import java.io.File

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day5/5-test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day5/5.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Long {
        return getData(data).min()
    }
}

