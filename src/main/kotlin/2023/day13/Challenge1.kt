package `2023`.day13

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day13/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day13/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val patterns = mutableListOf<List<String>>()
        var cursor = 0
        for (i in data.indices) {
            if (data[i]=="") {
                patterns.add(data.subList(cursor, i-1))
                cursor=i+1
            }
            if (data.size - 1 == i) {
                patterns.add(data.subList(cursor, i))
                cursor=i+1
            }
        }
        println(patterns.size)

        var symmetry = mutableListOf<Pair<Char, Int>>()

        for (pattern in patterns){
            for(i in pattern.indices){
                if (i<pattern.size-1){
                    if (pattern[i] == pattern[i+1]){
                        if (checkSymmetry(pattern, i)) {
                            symmetry.add(Pair('H', i))
                        }
                    }
                }
            }
        }

        val transposed = transpose(patterns)

        for (pattern in transposed){
            var found: Pair<Char, Int>? = null
            for(i in pattern.indices){
                if (i<pattern.size-1){
                    if (pattern[i] == pattern[i+1]){
                        if (checkSymmetry(pattern, i)) {
                            println("old: $found")
                            symmetry.add(Pair('V', i))
                            found = Pair('V', i)
                            println("new: ${Pair('V', i)}")
                        }
                    }
                }
            }
        }
        println(symmetry.filter { it.first=='V' }.size)
        println(symmetry.filter { it.first=='H' }.size)

        var res = 0
        for (axis in symmetry){
            if (axis.first == 'H'){
                res += (100*(axis.second+1))
            } else if (axis.first == 'V') {
                res += axis.second+1
            }
        }
        return res
    }

    private fun transpose(patterns: MutableList<List<String>>): MutableList<List<String>> {
        val result = mutableListOf<List<String>>()
        for (patern in patterns) {
            val newPattern = mutableListOf<String>()
            for (i in patern.first().indices) {
                newPattern.add("")
            }
            for (i in patern.first().indices) {
                for (line in patern) {
                    var idx = 0
                    for (j in line) {
                        newPattern[idx] = newPattern[idx] + j
                        idx++
                    }
                }
            }
            result.add(newPattern)
        }
        return result
    }

    private fun checkSymmetry(pattern: List<String>, index: Int): Boolean {
        var isMirror = true
        var subIndex = 0
        while (true){
            if (index - subIndex < 0 || index+1 + subIndex >= pattern.size) break
            if (pattern[index - subIndex] != pattern[index+1 + subIndex]){
                isMirror = false
                break
            }
            subIndex++
        }
        return isMirror
    }
}

