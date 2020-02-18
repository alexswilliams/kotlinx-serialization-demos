package io.github.alexswilliams.serialisation.kotlinx.demo2bmissingnullableprop

import io.github.alexswilliams.serialisation.classpath
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class Cat(
    val Name: String,
    val Age: Int,
    val Weight: Float,
    val Colour: String?
)

fun main() {
    val inputString = classpath("/demo2/single-cat-missing-prop.json").readText()

    val jsonMapper = Json(JsonConfiguration.Stable)
    val parsedCat = jsonMapper.parse(Cat.serializer(), inputString)

    println(parsedCat) // Field 'Colour' is required, but it was missing
}
