package `2023`.day7

import java.io.File
import java.lang.RuntimeException

class Challenge1 {
//    private val data: List<String> = File("src/main/resources/2023/Day7/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day7/7-adam.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): Long {
        val pairs = parse(data)
        val sortedPairs = sortPairs(pairs)
        return (sortedPairs zip sortedPairs.indices.map { it + 1 })
            .sumOf { it.first.second * it.second }
    }

    fun sortPairs(pairs: List<Pair<String, Long>>): List<Pair<String, Long>> {
        val result = mutableListOf<Pair<String, Long>>()
        for (element in pairs) {
            for (j in 0..<result.size) {
                val resultPair = result[j]
                val isStronger = comparePairs(element, resultPair)
                if (!isStronger) {
                    result.add(j, element)
                    break
                }
            }
            if (!result.contains(element)) {
                result.add(element)
            }
        }
        return result
    }

    private fun comparePairs(pair: Pair<String, Long>, resultPair: Pair<String, Long>): Boolean {
        val pairValue = getHandValue(pair.first)
        val resultPairValue = getHandValue(resultPair.first)
        return when {
            pairValue > resultPairValue -> true
            pairValue < resultPairValue -> false
            else -> {
                var res = false
                for (i in 0..<pair.first.length) {
                    if (CardValue.getEnum(pair.first[i]) > CardValue.getEnum(resultPair.first[i])) {
                        res = true
                        break
                    } else if (CardValue.getEnum(pair.first[i]) < CardValue.getEnum(resultPair.first[i])) {
                        res =  false
                        break
                    }
                }
                res
            }
        }
    }

    private fun getHandValue(hand: String): HandValue {
        return when {
            isFive(hand) -> HandValue.FIVE_OF_A_KIND
            isFour(hand) -> HandValue.FOUR_OF_A_KIND
            isFullHouse(hand) -> HandValue.FULL_HOUSE
            isThree(hand) -> HandValue.THREE_OF_A_KIND
            isTwoPairs(hand) -> HandValue.TWO_PAIRS
            isPair(hand) -> HandValue.PAIR
            else -> HandValue.HIGH_CARD
        }
    }

    private fun isPair(hand: String): Boolean {
        val values = hand.map { CardValue.getEnum(it) }
        return values.distinct().count() == 4
    }

    private fun isTwoPairs(hand: String): Boolean {
        val values = hand.map { CardValue.getEnum(it) }
        val valuesCounts = values.groupingBy { it }.eachCount().values
        return values.distinct().count() == 3 && valuesCounts.count { it == 2 } == 2
    }

    private fun isThree(hand: String): Boolean {
        val values = hand.map { CardValue.getEnum(it) }
        return values.distinct().count() == 3 && values.groupingBy { it }.eachCount().values.contains(3)
    }

    private fun isFullHouse(hand: String): Boolean {
        val values = hand.map { CardValue.getEnum(it) }
        val valuesCounts = values.groupingBy { it }.eachCount().values
        return values.distinct().count() == 2 && valuesCounts.contains(3) && valuesCounts.contains(2)
    }

    private fun isFour(hand: String): Boolean {
        val values = hand.map { CardValue.getEnum(it) }
        return values.distinct().count() == 2 && values.groupingBy { it }.eachCount().values.contains(4)
    }

    fun isFive(input: String): Boolean {
        return input.chars().distinct().count() == 1L
    }

    fun parse(input: List<String>): List<Pair<String, Long>> =
        input.map {
            val elements = it.split(" ")
            Pair(elements[0], elements[1].toLong())
        }

    enum class HandValue {
        HIGH_CARD,
        PAIR,
        TWO_PAIRS,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND,
    }

    enum class CardValue(val value: String) {
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("T"),
        JACK("J"),
        QUEEN("Q"),
        KING("K"),
        ACE("A");

        companion object {
            fun getEnum(value: Char): CardValue {
                return when (value) {
                    '2' -> TWO
                    '3' -> THREE
                    '4' -> FOUR
                    '5' -> FIVE
                    '6' -> SIX
                    '7' -> SEVEN
                    '8' -> EIGHT
                    '9' -> NINE
                    'T' -> TEN
                    'J' -> JACK
                    'Q' -> QUEEN
                    'K' -> KING
                    'A' -> ACE
                    else -> { throw RuntimeException()}
                }
            }
        }
    }
}

