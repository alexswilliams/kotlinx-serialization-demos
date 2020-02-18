package io.github.alexswilliams.serialisation.jackson.benchmarks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.alexswilliams.serialisation.classpath
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    val singleInput = classpath("/benchmarks/singleInput.json").readText()

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())

    val firstInvocationSingleMillis = measureTimeMillis { jsonMapper.readValue(singleInput, Cat::class.java) }
    println("First Invocation of a single input: $firstInvocationSingleMillis ms")

    // Burn through many repetitions to avoid e.g. JIT and JNI complications
    repeat(1000) { jsonMapper.readValue(singleInput, Cat::class.java) }

    val iterations =
        (1..100_000).map { measureNanoTime { jsonMapper.readValue(singleInput, Cat::class.java) } }

    val mean = iterations.average()
    val stddev = sqrt(iterations.fold(0.0, { acc, next -> acc + (next - mean).pow(2.0) }) / iterations.size)

    println("${iterations.size} iterations of reading a single input:")

    println("Sum: ${iterations.sum() / 1_000_000} ms")
    println("Mean: $mean ns")
    println("Standard Deviation: $stddev ns")
    println("Min: ${iterations.min()} ns")
    println("Max: ${iterations.max()} ns")

    // First Invocation of a single input: 671 ms
    // 100000 iterations of reading a single input:
    // Sum: 533 ms
    // Mean: 5339.76141 ns
    // Standard Deviation: 42332.57873140346 ns
    // Min: 1503 ns
    // Max: 8499437 ns

}
