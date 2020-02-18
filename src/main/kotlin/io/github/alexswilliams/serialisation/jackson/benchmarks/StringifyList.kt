package io.github.alexswilliams.serialisation.jackson.benchmarks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
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

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())

    val firstInvocationSingleMillis = measureTimeMillis { jsonMapper.writeValueAsString(listOfCats) }
    println("First Invocation of a list of outputs: $firstInvocationSingleMillis ms")

    // Burn through many repetitions to avoid e.g. JIT and JNI complications
    repeat(1000) { jsonMapper.writeValueAsString(listOfCats) }

    val iterations =
        (1..100_000).map { measureNanoTime { jsonMapper.writeValueAsString(listOfCats) } }

    val mean = iterations.average()
    val stddev = sqrt(iterations.fold(0.0, { acc, next -> acc + (next - mean).pow(2.0) }) / iterations.size)

    println("${iterations.size} iterations of writing a list of outputs:")

    println("Sum: ${iterations.sum() / 1_000_000} ms")
    println("Mean: $mean ns")
    println("Standard Deviation: $stddev ns")
    println("Min: ${iterations.min()} ns")
    println("Max: ${iterations.max()} ns")

    // First Invocation of a list of outputs: 678 ms
    // 100000 iterations of writing a list of outputs:
    // Sum: 304 ms
    // Mean: 3049.61434 ns
    // Standard Deviation: 29603.60147294353 ns
    // Min: 1278 ns
    // Max: 6762105 ns

}
