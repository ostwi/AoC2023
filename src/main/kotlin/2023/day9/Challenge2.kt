package `2023`.day9

import java.io.File

class Challenge2 {
//    private val data: List<String> = File("src/main/resources/2023/Day9/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day9/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        return data.sumOf { it.getLastValue() }
    }
}

private fun String.getLastValue(): Int {
    val source = this.trim().split(" ").map { it.trim().toInt() }
    val differences = getDiffsBackwards(source)
    return source.first() - differences.first()
}

fun getDiffsBackwards(source: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (i in 0..<source.size - 1) {
        result.add(source[i + 1] - source[i])
    }
    return if (result.distinct().size == 1 && result.first() == 0) {
        result.add(0,0)
        result
    } else {
        result.add(0,result.first() - getDiffsBackwards(result).first())
        result
    }
}
