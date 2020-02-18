package io.github.alexswilliams.serialisation.kotlinx.demo4alists

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
)

enum class CatColour {
    Ginger, Black, White
}

fun main() {
    val inputString = classpath("/demo4/many-cats.json").readText()

    val jsonMapper = Json(JsonConfiguration.Stable)
    val parsedCats = jsonMapper.parse(Cat.serializer().list, inputString)

    println(parsedCats)
    //[
    // Cat(Name=Marmalade, Age=3, Weight=10.4, Colour=Ginger),
    // Cat(Name=Paprika, Age=6, Weight=12.2, Colour=Ginger),
    // Cat(Name=Mr MistoffeLeeds, Age=2, Weight=7.1, Colour=Black)
    //]
}
