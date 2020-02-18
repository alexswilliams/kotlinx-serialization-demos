package io.github.alexswilliams.serialisation.jackson.benchmarks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    val singleCat = Cat(Name = "Fluffy", ID = UUID.randomUUID(), Age = 5, Weight = 9.5f, Colour = Cat.CatColour.White)

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())

    val firstInvocationSingleMillis = measureTimeMillis { jsonMapper.writeValueAsString(singleCat) }
    println("First Invocation of a single output: $firstInvocationSingleMillis ms")

    // Burn through many repetitions to avoid e.g. JIT and JNI complications
    repeat(1000) { jsonMapper.writeValueAsString(singleCat) }

    val iterations =
        (1..100_000).map { measureNanoTime { jsonMapper.writeValueAsString(singleCat) } }

    val mean = iterations.average()
    val stddev = sqrt(iterations.fold(0.0, { acc, next -> acc + (next - mean).pow(2.0) }) / iterations.size)

    println("${iterations.size} iterations of writing a single output:")

    println("Sum: ${iterations.sum() / 1_000_000} ms")
    println("Mean: $mean ns")
    println("Standard Deviation: $stddev ns")
    println("Min: ${iterations.min()} ns")
    println("Max: ${iterations.max()} ns")

    // First Invocation of a single output: 739 ms
    // 100000 iterations of writing a single output:
    // Sum: 268 ms
    // Mean: 2686.69726 ns
    // Standard Deviation: 41772.03477344896 ns
    // Min: 1137 ns
    // Max: 10765906 ns

}
