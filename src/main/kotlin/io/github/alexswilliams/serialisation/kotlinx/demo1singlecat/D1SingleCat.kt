package io.github.alexswilliams.serialisation.kotlinx.demo1singlecat

import io.github.alexswilliams.serialisation.classpath
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class Cat(
    val Name: String,
    val Age: Int,
    val Weight: Float,
    val Colour: String
)

fun main() {
    val inputString = classpath("/demo1/single-cat.json").readText()

    val jsonMapper = Json(JsonConfiguration.Stable)
    val parsedCat = jsonMapper.parse(Cat.Companion.serializer(), inputString)

    println(parsedCat) // Cat(Name=Marmalade, Age=3, Weight=10.4, Colour=Ginger)
}
