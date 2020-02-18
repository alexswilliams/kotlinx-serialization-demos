package io.github.alexswilliams.serialisation.jackson.demo2amissingprop

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.alexswilliams.serialisation.classpath

data class Cat(
    val Name: String,
    val Age: Int,
    val Weight: Float,
    val Colour: String
)

fun main() {
    val inputString = classpath("/demo2/single-cat-missing-prop.json").readText()

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())
    val parsedCat = jsonMapper.readValue(inputString, Cat::class.java)

    println(parsedCat) // value failed for JSON property Colour due to missing (therefore NULL) value for creator parameter Colour which is a non-nullable type
}
