package `2023`.day12

import java.io.File

class Challenge2 {
    private val data: List<String> = File("src/main/resources/2023/Day12/test.txt").readLines()

    fun run() {
        val unfoldedData = unfoldData(data)
        val totalArrangements = unfoldedData.sumOf { line ->
            val (pattern, groupsString) = line.split(" ")
            val groups = groupsString.split(",").map { it.toInt() }
            countValidCombinations(pattern, groups)
        }
        println("Total number of arrangements: $totalArrangements")
    }

    private fun unfoldData(data: List<String>): List<String> {
        return data.map { line ->
            val (pattern, groupsString) = line.split(" ")
            val unfoldedPattern = unfoldPattern(pattern)
            val unfoldedGroups = unfoldGroups(groupsString)
            "$unfoldedPattern $unfoldedGroups"
        }
    }

    private fun unfoldPattern(pattern: String): String {
        return (1..5).joinToString("?") { pattern }
    }

    private fun unfoldGroups(groupsString: String): String {
        return (1..5).joinToString(",") { groupsString }
    }

    private fun countValidCombinations(pattern: String, groups: List<Int>): Int {
        return countHelper(pattern, groups, 0)
    }

    private fun countHelper(pattern: String, groups: List<Int>, index: Int): Int {
        if (index == pattern.length) {
            return if (isValidPattern(pattern, groups)) 1 else 0
        }

        if (pattern[index] != '?') {
            return countHelper(pattern, groups, index + 1)
        }

        val replacedWithDot = pattern.substring(0, index) + '.' + pattern.substring(index + 1)
        val replacedWithHash = pattern.substring(0, index) + '#' + pattern.substring(index + 1)

        return countHelper(replacedWithDot, groups, index + 1) + countHelper(replacedWithHash, groups, index + 1)
    }

    private fun isValidPattern(pattern: String, groups: List<Int>): Boolean {
        val actualGroups = mutableListOf<Int>()
        var count = 0

        for (ch in pattern) {
            if (ch == '#') {
                count++
            } else if (count > 0) {
                actualGroups.add(count)
                count = 0
            }
        }
        if (count > 0) actualGroups.add(count)

        return actualGroups == groups
    }
}