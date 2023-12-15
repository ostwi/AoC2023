package `2023`.day14

import java.io.File
import kotlin.math.abs

class Challenge2 {
//        private val data: List<String> = File("src/main/resources/2023/Day14/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day14/data.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Int {
        val matrix = data.map { it.toCharArray() }.toTypedArray()
        val values = mutableListOf<Pair<Int, Int>>()
        for (i in 0..10000){
            matrix.turn()
            values.add(Pair(i, calculatePressure(matrix)))
        }
        val x = values.toList().subList(260, 288)
        val start = 260
        var cnt = 260L
        var done = false
        var res = 0L
        while (!done){
            if(cnt + 28 > 1000000000) {
                res = 1000000000 - cnt
                done = true
                break
            }
            cnt += 28
        }
        println(res)
        println(x)
        return calculatePressure(matrix)
    }

    private fun calculatePressure(matrix: Array<CharArray>): Int {
        var sum = 0
        for (i in matrix.size-1 downTo 0) {
            for (sign in matrix[i]){
                if(sign == 'O') {
                    sum += (matrix.size-i)
                }
            }
        }
        return sum
    }

    private fun Array<CharArray>.turn() {
        this.tiltNorth()
//        printMatrix(this)
//        println("=========")
        this.tiltWest()
//        printMatrix(this)
//        println("=========")
        this.tiltSouth()
//        printMatrix(this)
//        println("=========")
        this.tiltEast()
//        printMatrix(this)
//        println("=========")
    }

    private fun printMatrix(matrix: Array<CharArray>) {
        for (e in matrix) {
            println(e)
        }
    }


}

fun Array<CharArray>.tiltNorth() {
    for (i in 0..<this[0].size) {
        for (j in indices){
            if (this[j][i]=='O'){
                this.moveNorth(i, j)
            }
        }
    }
}

fun Array<CharArray>.tiltWest() {
    for (j in indices) {
        for (i in this[0].indices){
            if (this[j][i]=='O'){
                this.moveWest(i, j)
            }
        }
    }
}

fun Array<CharArray>.tiltSouth() {
    for (i in this[0].size-1 downTo 0) {
        for (j in this.size-1 downTo 0){
            if (this[j][i]=='O'){
                this.moveSouth(i, j)
            }
        }
    }
}

fun Array<CharArray>.tiltEast() {
    for (i in this[0].size-1 downTo 0) {
        for (j in this.size-1 downTo 0){
            if (this[j][i]=='O'){
                this.moveEast(i, j)
            }
        }
    }
}

private fun Array<CharArray>.moveNorth(h: Int, v: Int) {
    var stepsNorth = 0
    for (i in v downTo  0){
        if( v == i){
            continue
        }
        if (this[i][h]=='.'){
            stepsNorth++
            if (i == 0) {
                this[v-stepsNorth][h] = 'O'
                this[v][h] = '.'
            }
        } else if(i == 0 && stepsNorth == 0){
            return
        } else {
            if (stepsNorth>0){
                this[v-stepsNorth][h] = 'O'
                this[v][h] = '.'
            }
            break
        }
    }
}

private fun Array<CharArray>.moveWest(h: Int, v: Int) {
    var steps = 0
    for (i in h downTo  0){
        if( h == i){
            continue
        }
        if (this[v][i]=='.'){
            steps++
            if (i == 0) {
                this[v][h - steps] = 'O'
                this[v][h] = '.'
            }
        } else if(i == 0 && steps == 0){
            return
        }
        else {
            if (steps>0){
                this[v][h-steps] = 'O'
                this[v][h] = '.'
            }
            break
        }
    }
}

private fun Array<CharArray>.moveSouth(h: Int, v: Int) {
    var steps = 0
    for (i in v ..<this.size){
        if( v == i){
            continue
        }
        if (this[i][h]=='.'){
            steps++
            if (i == this.size-1) {
                this[v+steps][h] = 'O'
                this[v][h] = '.'
            }
        } else if(i == this.size-1 && steps == 0){
            return
        } else {
            if (steps>0){
                this[v+steps][h] = 'O'
                this[v][h] = '.'
            }
            break
        }
    }
}

private fun Array<CharArray>.moveEast(h: Int, v: Int) {
    var steps = 0
    for (i in h ..<this[0].size){
        if( h == i){
            continue
        }
        if (this[v][i]=='.'){
            steps++
            if (i == this.size-1) {
                this[v][h+steps] = 'O'
                this[v][h] = '.'
            }
        }else if(i == this[0].size-1 && steps == 0){
            return
        } else {
            if (steps>0){
                this[v][h+steps] = 'O'
                this[v][h] = '.'
            }
            break
        }
    }
}

