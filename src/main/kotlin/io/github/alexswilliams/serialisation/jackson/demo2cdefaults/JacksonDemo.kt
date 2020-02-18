package io.github.alexswilliams.serialisation.jackson.demo2cdefaults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.alexswilliams.serialisation.classpath

data class Cat(
    val Name: String,
    val Age: Int,
    val Weight: Float,
    val Colour: String = "Unknown"
)

fun main() {
    val inputString = classpath("/demo2/single-cat-missing-prop.json").readText()

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())
    val parsedCat = jsonMapper.readValue(inputString, Cat::class.java)

    println(parsedCat) // Cat(Name=Marmalade, Age=3, Weight=10.4, Colour=Unknown)
}
