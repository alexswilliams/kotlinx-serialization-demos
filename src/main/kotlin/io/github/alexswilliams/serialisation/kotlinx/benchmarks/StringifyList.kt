package io.github.alexswilliams.serialisation.kotlinx.benchmarks

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    val listOfCats = listOf(
        Cat(Name = "Fluffy", ID = UUID.randomUUID(), Age = 5, Weight = 9.5f, Colour = Cat.CatColour.White),
        Cat(Name = "Whiskers", ID = UUID.randomUUID(), Age = 3, Weight = 10.6f, Colour = Cat.CatColour.Black),
        Cat(Name = "Princess", ID = UUID.randomUUID(), Age = 7, Weight = 12.0f, Colour = Cat.CatColour.Ginger)
    )

    val jsonMapper = Json(JsonConfiguration.Stable)


    val firstInvocationSingleMillis = measureTimeMillis { jsonMapper.stringify(Cat.serializer().list, listOfCats) }
    println("First Invocation of a list of outputs: $firstInvocationSingleMillis ms")

    // Burn through many repetitions to avoid e.g. JIT and JNI complications
    repeat(1000) { jsonMapper.stringify(Cat.serializer().list, listOfCats) }

    val iterations =
        (1..100_000).map { measureNanoTime { jsonMapper.stringify(Cat.serializer().list, listOfCats) } }

    val mean = iterations.average()
    val stddev = sqrt(iterations.fold(0.0, { acc, next -> acc + (next - mean).pow(2) }) / iterations.size)

    println("${iterations.size} iterations of writing a list of outputs:")

    println("Sum: ${iterations.sum() / 1_000_000} ms")
    println("Mean: $mean ns")
    println("Standard Deviation: $stddev ns")
    println("Min: ${iterations.min()} ns")
    println("Max: ${iterations.max()} ns")

    // First Invocation of a list of outputs: 27 ms
    // 100000 iterations of writing a list of outputs:
    // Sum: 644 ms
    // Mean: 6440.0762 ns
    // Standard Deviation: 24045.87334686457 ns
    // Min: 2527 ns
    // Max: 5410712 ns

}
