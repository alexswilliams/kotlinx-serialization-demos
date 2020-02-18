package io.github.alexswilliams.serialisation.jackson.demo1singlecat

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
    val inputString = classpath("/demo1/single-cat.json").readText()

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())
    val parsedCat = jsonMapper.readValue(inputString, Cat::class.java)

    println(parsedCat) // Cat(Name=Marmalade, Age=3, Weight=10.4, Colour=Ginger)
}
