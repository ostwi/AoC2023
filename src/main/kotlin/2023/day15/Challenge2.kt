package `2023`.day15

import java.io.File
import java.lang.RuntimeException
import kotlin.streams.toList

class Challenge2 {
//        private val data: List<String> = File("src/main/resources/2023/Day15/test.txt").readLines().first().split(",")
    private val data: List<String> = File("src/main/resources/2023/Day15/data.txt").readLines().first().split(",")

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val instructions = data.map { it.mapToInstruction() }
        val boxes = Array<List<Pair<String, Int?>>>(256) { emptyList() }
        for (i in instructions) {
            if (i.op == '=') {
                if (boxes[i.label.decode()].map { it.first }.contains(i.label)) {
                    boxes[i.label.decode()] = boxes[i.label.decode()].replace(Pair(i.label, i.focalLength))
                } else {
                    boxes[i.label.decode()] = boxes[i.label.decode()].plus(Pair(i.label, i.focalLength))
                }
            } else {
                boxes[i.label.decode()] = boxes[i.label.decode()].remove(Pair(i.label, i.focalLength))
            }
        }
        for (i in boxes) {
            if (i.isNotEmpty()) {
                println(i.map { "${it.first.decode()} ${it.second}"})
            }
        }
        return fullCalc(boxes)
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

    fun fullCalc(input: Array<List<Pair<String, Int?>>>): Int {
        var res = 0
        for (i in 0..255) {
            var slot = 1
            for (j in input[i]) {
                println("$slot, ${j.first.decode()}, ${j.second!!}")
                res += slot * (j.first.decode()+1) * j.second!!
                slot++
            }
        }
        return res
    }
    private fun List<Pair<String, Int?>>.remove(pair: Pair<String, Int?>): List<Pair<String, Int?>> {
        val new = mutableListOf<Pair<String, Int?>>()
        for (element in this) {
            val shouldRemove = element.first == pair.first
            if (!shouldRemove){
                new.add(element)
            }
        }
        return new
    }

    private fun List<Pair<String, Int?>>.replace(pair: Pair<String, Int?>): List<Pair<String, Int?>> {
        val new = mutableListOf<Pair<String, Int?>>()
        println(this)
        for (element in this) {
            val shouldReplace = element.first == pair.first
            if (shouldReplace){
                new.add(pair)
            } else {
                new.add(element)
            }
        }
        return new
    }

    data class Instruction(val op: Char, val label: String, val focalLength: Int?)

    private fun String.mapToInstruction(): Instruction {
        if (this.contains('=')) {
            this.split('=').let {
                return Instruction('=', it[0], it[1].toIntOrNull())
            }
        } else {
            this.split('-').let {
                return Instruction('-', it[0], null)
            }
        }
    }

    private fun String.decode(): Int {
        return calc(this.chars().toList())
    }
}

