package io.github.alexswilliams.serialisation.kotlinx.benchmarks

import io.github.alexswilliams.serialisation.classpath
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    val listInput = classpath("/benchmarks/listInput.json").readText()

    val jsonMapper = Json(JsonConfiguration.Stable)


    val firstInvocationSingleMillis = measureTimeMillis { jsonMapper.parse(Cat.serializer().list, listInput) }
    println("First Invocation of a list of inputs: $firstInvocationSingleMillis ms")

    // Burn through many repetitions to avoid e.g. JIT and JNI complications
    repeat(1000) { jsonMapper.parse(Cat.serializer().list, listInput) }

    val iterations =
        (1..100_000).map { measureNanoTime { jsonMapper.parse(Cat.serializer().list, listInput) } }

    val mean = iterations.average()
    val stddev = sqrt(iterations.fold(0.0, { acc, next -> acc + (next - mean).pow(2.0) }) / iterations.size)

    println("${iterations.size} iterations of reading a list of inputs:")

    println("Sum: ${iterations.sum() / 1_000_000} ms")
    println("Mean: $mean ns")
    println("Standard Deviation: $stddev ns")
    println("Min: ${iterations.min()} ns")
    println("Max: ${iterations.max()} ns")

    // First Invocation of a list of inputs: 59 ms
    // 100000 iterations of reading a list of inputs:
    // Sum: 1403 ms
    // Mean: 14034.10602 ns
    // Standard Deviation: 106295.2320442576 ns
    // Min: 3185 ns
    // Max: 13996812 ns

}
