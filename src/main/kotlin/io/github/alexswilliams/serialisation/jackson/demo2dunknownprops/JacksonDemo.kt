package io.github.alexswilliams.serialisation.jackson.demo2dunknownprops

import com.fasterxml.jackson.databind.DeserializationFeature
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
    val inputString = classpath("/demo2/single-cat-extra-prop.json").readText()

    val jsonMapper = ObjectMapper().registerModule(KotlinModule()).apply {
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }
    val parsedCat = jsonMapper.readValue(inputString, Cat::class.java)

    println(parsedCat) // Cat(Name=Marmalade, Age=3, Weight=10.4, Colour=Ginger)
}
