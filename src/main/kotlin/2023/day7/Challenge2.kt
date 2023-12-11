package `2023`.day7

import java.io.File
import java.lang.RuntimeException

class Challenge2 {
//        private val data: List<String> = File("src/main/resources/2023/Day7/test.txt").readLines()
    private val data: List<String> = File("src/main/resources/2023/Day7/7-adam.txt").readLines()

    fun run() {
        println(getResult())
    }

    private fun getResult(): ULong {
        val pairs = parse(data)
        val sortedPairs = sortPairs(pairs)
        val zipped = (sortedPairs zip sortedPairs.indices.map { it + 1 })
        zipped.map { println(it) }
        val res = zipped.sumOf { it.first.second.toULong() * it.second.toULong() }
        return res
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
        val value = when {
            isFive(hand) -> HandValue.FIVE_OF_A_KIND
            isFour(hand) -> HandValue.FOUR_OF_A_KIND
            isFullHouse(hand) -> HandValue.FULL_HOUSE
            isThree(hand) -> HandValue.THREE_OF_A_KIND
            isTwoPairs(hand) -> HandValue.TWO_PAIRS
            isPair(hand) -> HandValue.PAIR
            else -> HandValue.HIGH_CARD
        }
        return checkJokers(hand, value)
    }

    private fun checkJokers(hand: String, value: HandValue): HandValue {
        val jokersCount = hand.count { it == 'J' }
        if (jokersCount == 0) {
            return value
        } else if (jokersCount == 1) {
            return when(value) {
                HandValue.HIGH_CARD -> HandValue.PAIR
                HandValue.PAIR -> HandValue.THREE_OF_A_KIND
                HandValue.TWO_PAIRS -> HandValue.FULL_HOUSE
                HandValue.THREE_OF_A_KIND -> HandValue.FOUR_OF_A_KIND
                HandValue.FULL_HOUSE -> throw RuntimeException()
                HandValue.FOUR_OF_A_KIND -> HandValue.FIVE_OF_A_KIND
                HandValue.FIVE_OF_A_KIND -> throw RuntimeException()
            }
        } else if (jokersCount == 2) {
            return when(value) {
                HandValue.HIGH_CARD -> throw RuntimeException()
                HandValue.PAIR -> HandValue.THREE_OF_A_KIND
                HandValue.TWO_PAIRS -> HandValue.FOUR_OF_A_KIND
                HandValue.THREE_OF_A_KIND -> HandValue.FIVE_OF_A_KIND
                HandValue.FULL_HOUSE -> HandValue.FIVE_OF_A_KIND
                HandValue.FOUR_OF_A_KIND -> throw RuntimeException()
                HandValue.FIVE_OF_A_KIND -> throw RuntimeException()
            }
        } else if (jokersCount == 3) {
            return when(value) {
                HandValue.HIGH_CARD -> throw RuntimeException()
                HandValue.PAIR -> throw RuntimeException()
                HandValue.TWO_PAIRS -> throw RuntimeException()
                HandValue.THREE_OF_A_KIND -> HandValue.FIVE_OF_A_KIND
                HandValue.FULL_HOUSE -> HandValue.FIVE_OF_A_KIND
                HandValue.FOUR_OF_A_KIND -> throw RuntimeException()
                HandValue.FIVE_OF_A_KIND -> throw RuntimeException()
            }
        } else if (jokersCount > 3) {
            return HandValue.FIVE_OF_A_KIND
        } else {
            throw RuntimeException()
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
        JOKER("J"),
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
                    'J' -> JOKER
                    '2' -> TWO
                    '3' -> THREE
                    '4' -> FOUR
                    '5' -> FIVE
                    '6' -> SIX
                    '7' -> SEVEN
                    '8' -> EIGHT
                    '9' -> NINE
                    'T' -> TEN
                    'Q' -> QUEEN
                    'K' -> KING
                    'A' -> ACE
                    else -> { throw RuntimeException()}
                }
            }
        }
    }
}

