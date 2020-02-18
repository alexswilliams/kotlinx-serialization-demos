package io.github.alexswilliams.serialisation.jackson.benchmarks

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.alexswilliams.serialisation.classpath
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    val listInput = classpath("/benchmarks/listInput.json").readText()

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())
    val listOfCats = object : TypeReference<List<Cat>>() {}

    val firstInvocationListMillis = measureTimeMillis { jsonMapper.readValue(listInput, listOfCats) }
    println("First Invocation of a list of inputs: $firstInvocationListMillis ms")

    // Burn through many repetitions to avoid e.g. JIT and JNI complications
    repeat(1000) { jsonMapper.readValue(listInput, listOfCats) }

    val iterations =
        (1..100_000).map { measureNanoTime { jsonMapper.readValue(listInput, listOfCats) } }

    val mean = iterations.average()
    val stddev = sqrt(iterations.fold(0.0, { acc, next -> acc + (next - mean).pow(2.0) }) / iterations.size)

    println("${iterations.size} iterations of reading a list of inputs:")

    println("Sum: ${iterations.sum() / 1_000_000} ms")
    println("Mean: $mean ns")
    println("Standard Deviation: $stddev ns")
    println("Min: ${iterations.min()} ns")
    println("Max: ${iterations.max()} ns")

    // First Invocation of a list of inputs: 1378 ms
    // 100000 iterations of reading a list of inputs:
    // Sum: 861 ms
    // Mean: 8616.91878 ns
    // Standard Deviation: 34514.87512788058 ns
    // Min: 4526 ns
    // Max: 6813719 ns

}
