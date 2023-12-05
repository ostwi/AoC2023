package `2023`.day5

import kotlin.math.min

data class Element(
    val destinationStart: Long,
    val sourceStart: Long,
    val length: Long
)

fun getData(input: List<String>): List<Long> {
    val seeds = input.first().substringAfter(": ").split(" ").map { it.toLong() }
    val maps = input.subList(1, input.size)
    val seedToSoil = getMap(maps, "seed-to-soil map:")
    val soilToFertilizer = getMap(maps, "soil-to-fertilizer map:")
    val fertilizerToWater = getMap(maps, "fertilizer-to-water map:")
    val waterToLight = getMap(maps, "water-to-light map:")
    val lightToTemperature = getMap(maps, "light-to-temperature map:")
    val temperatureToHumidity = getMap(maps, "temperature-to-humidity map:")
    val humidityToLocation = getMap(maps, "humidity-to-location map:")

    val x = seeds.flatMap { seed: Long ->
        seedToSoil
            .filter { it.isInRange(seed) }
            .map { it.findDestination(seed) }
            .ifEmpty { listOf(seed) }
    }.flatMap { soil ->
        soilToFertilizer
            .filter { it.isInRange(soil) }
            .map { it.findDestination(soil) }
            .ifEmpty { listOf(soil) }
    }.flatMap { fertilizer ->
        fertilizerToWater
            .filter { it.isInRange(fertilizer) }
            .map { it.findDestination(fertilizer) }
            .ifEmpty { listOf(fertilizer) }
    }
        .flatMap { water ->
        waterToLight
            .filter { it.isInRange(water) }
            .map { it.findDestination(water) }
            .ifEmpty { listOf(water) }
    }
        .flatMap { light ->
        lightToTemperature
            .filter { it.isInRange(light) }
            .map { it.findDestination(light) }
            .ifEmpty { listOf(light) }
    }
        .flatMap { temp ->
        temperatureToHumidity
            .filter { it.isInRange(temp) }
            .map { it.findDestination(temp) }
            .ifEmpty { listOf(temp) }
    }
        .flatMap { humidity ->
        humidityToLocation
            .filter { it.isInRange(humidity) }
            .map { it.findDestination(humidity) }
            .ifEmpty { listOf(humidity) }
    }.toList()

    return x
}

fun getData2(input: List<String>): Long {
    val seeds = input.first().substringAfter(": ").split(" ").map { it.toLong() }
    val maps = input.subList(1, input.size)
    val seedToSoil = getMap(maps, "seed-to-soil map:")
    val soilToFertilizer = getMap(maps, "soil-to-fertilizer map:")
    val fertilizerToWater = getMap(maps, "fertilizer-to-water map:")
    val waterToLight = getMap(maps, "water-to-light map:")
    val lightToTemperature = getMap(maps, "light-to-temperature map:")
    val temperatureToHumidity = getMap(maps, "temperature-to-humidity map:")
    val humidityToLocation = getMap(maps, "humidity-to-location map:")

    var result = 0L
    for (i in seeds.indices) {
        println(seeds[i])
        if (i%2 == 0) {
            val range = (seeds[i]..<seeds[i] + seeds[i+1])
            for (seedValue in range){
                val u = seedValue.let { seed: Long ->
                    seedToSoil
                        .filter { it.isInRange(seed) }
                        .map { it.findDestination(seed) }
                        .ifEmpty { listOf(seed) }
                }.flatMap { soil ->
//                    println("Soil: $soil")
                    soilToFertilizer
                        .filter { it.isInRange(soil) }
                        .map { it.findDestination(soil) }
                        .ifEmpty { listOf(soil) }
                }.flatMap { fertilizer ->
//                    println("Fertilizer: $fertilizer")
                    fertilizerToWater
                        .filter { it.isInRange(fertilizer) }
                        .map { it.findDestination(fertilizer) }
                        .ifEmpty { listOf(fertilizer) }
                }
                    .flatMap { water ->
//                        println("Water: $water")
                        waterToLight
                            .filter { it.isInRange(water) }
                            .map { it.findDestination(water) }
                            .ifEmpty { listOf(water) }
                    }
                    .flatMap { light ->
//                        println("Light: $light")
                        lightToTemperature
                            .filter { it.isInRange(light) }
                            .map { it.findDestination(light) }
                            .ifEmpty { listOf(light) }
                    }
                    .flatMap { temp ->
//                        println("Temp: $temp")
                        temperatureToHumidity
                            .filter { it.isInRange(temp) }
                            .map { it.findDestination(temp) }
                            .ifEmpty { listOf(temp) }
                    }
                    .flatMap { humidity ->
//                        println("Humidity: $humidity")
                        humidityToLocation
                            .filter { it.isInRange(humidity) }
                            .map { it.findDestination(humidity) }
                            .ifEmpty { listOf(humidity) }
                    }.toList()
                result = if (result == 0L) {
                    u.min()
                } else {
                    min(result, u.min())
                }
            }
        }
    }

    return result
}

fun getMap(input: List<String>, header: String): List<Element> {
    val start = input.indexOf(header) + 1
    val end = input.subList(start, input.size).indexOf("")
    val data = if (end < 0 ) {
        input.subList(start, input.size)
    } else {
        input.subList(start, start + end)
    }
    val map = data.map { line ->
        line.trim()
            .split(" ")
            .map { it.toLong() }
            .let { Element(it[0], it[1], it[2]) }
    }
    return map
}

fun Element.findDestination(input: Long): Long {
    return if (input in sourceStart..<sourceStart + length) {
        destinationStart + (input - sourceStart)
    } else {
        input
    }
}

fun Element.isInRange(input: Long): Boolean {
    return input in sourceStart..<sourceStart + length
}
