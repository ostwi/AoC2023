package `2023`.day19

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
//        private val data: List<String> = File("src/main/resources/2023/Day19/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day19/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    fun getResult(): Long {
        val processes =  getProcesses()
        val inputs = getInputs()
        return inputs.map { it to follow(processes, it, "in") }
            .filter { it.second == 'A' }
            .sumOf { it.first.values.sum() }
    }

    private fun follow(processes: Map<String, List<String>>, input: Map<String, Long>, s: String): Char {
        val proc = processes[s] ?: throw RuntimeException("No process found for $s")
        for (i in proc.indices) {
            val temp = proc[i].calc(input)
            if (temp == "A") {
                return 'A'
            } else if (temp == "R") {
                return 'R'
            } else if (temp != null) {
                return follow(processes, input, temp)
            }
        }
        return 'N'
    }

    fun String.calc(input: Map<String, Long>): String? {
        if(this.contains(":")) {
            val next = this.split(":")[1]
            val exp = this.split(":")[0]
            if(exp.contains("<")) {
                return if (getValue(exp.split("<")[0], input) < exp.split("<")[1].toInt()) {
                    next
                } else {
                    null
                }
            } else if (exp.contains(">")) {
                return if (getValue(exp.split(">")[0], input) > exp.split(">")[1].toInt()) {
                    next
                } else {
                    null
                }
            } else {
                throw RuntimeException("Unknown operator")
            }
        } else {
            return this
        }
    }

    fun getValue(s: String, input: Map<String, Long>): Long {
        return input[s] ?: throw RuntimeException("No value found for $s")
    }

    private fun getInputs(): List<Map<String, Long>> {
        return data.subList(data.indexOf("") + 1, data.size)
            .map { it.drop(1) }
            .map { it.dropLast(1) }
            .map { it.split(",") }
            .map { eq ->
                eq.associate { it.split("=")[0] to it.split("=")[1].toLong() }
            }
    }

    private fun getProcesses(): Map<String, List<String>> {
        return data.subList(0, data.indexOf(""))
            .map {
                    it.split("{")[0] to
                    it.split("{")[1].split("}")[0].split(",")
            }.toMap()
    }

    data class Proc(val name: String, val children: List<String>)
    data class Input(val elements: Map<String, Long>)
}
