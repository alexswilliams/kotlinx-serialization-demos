package io.github.alexswilliams.serialisation.kotlinx.benchmarks

import io.github.alexswilliams.serialisation.classpath
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    val singleInput = classpath("/benchmarks/singleInput.json").readText()

    val jsonMapper = Json(JsonConfiguration.Stable)


    val firstInvocationSingleMillis = measureTimeMillis { jsonMapper.parse(Cat.serializer(), singleInput) }
    println("First Invocation of a single input: $firstInvocationSingleMillis ms")

    // Burn through many repetitions to avoid e.g. JIT and JNI complications
    repeat(1000) { jsonMapper.parse(Cat.serializer(), singleInput) }

    val iterations =
        (1..100_000).map { measureNanoTime { jsonMapper.parse(Cat.serializer(), singleInput) } }

    val mean = iterations.average()
    val stddev = sqrt(iterations.fold(0.0, { acc, next -> acc + (next - mean).pow(2) }) / iterations.size)

    println("${iterations.size} iterations of reading a single input:")

    println("Sum: ${iterations.sum() / 1_000_000} ms")
    println("Mean: $mean ns")
    println("Standard Deviation: $stddev ns")
    println("Min: ${iterations.min()} ns")
    println("Max: ${iterations.max()} ns")

    // First Invocation of a single input: 34 ms
    // 100000 iterations of reading a single input:
    // Sum: 1103 ms
    // Mean: 11036.46927 ns
    // Standard Deviation: 92016.87750656382 ns
    // Min: 1621 ns
    // Max: 11253338 ns

}
