package io.github.alexswilliams.serialisation.kotlinx.demo4bjunklists

import io.github.alexswilliams.serialisation.classpath
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list

@Serializable
data class Cat(
    val Name: String,
    val Age: Int,
    val Weight: Float,
    val Colour: CatColour
) {
    enum class CatColour {
        Ginger, Black, White
    }
}

fun main() {
    val inputString = classpath("/demo4/many-badly-formed-cats.json").readText()

    val jsonMapper = Json(JsonConfiguration.Stable)
    val parsedCats = jsonMapper.parse(Cat.serializer().list, inputString)
    // Encountered an unknown key: ID
    println(parsedCats)
}
