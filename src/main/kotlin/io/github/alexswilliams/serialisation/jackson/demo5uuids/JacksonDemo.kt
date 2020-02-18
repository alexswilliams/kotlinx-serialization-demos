package io.github.alexswilliams.serialisation.jackson.demo5uuids

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.alexswilliams.serialisation.classpath
import java.util.*

data class Cat(
    val Name: String,
    val ID: UUID,
    val Age: Int,
    val Weight: Float,
    val Colour: CatColour
) {
    enum class CatColour {
        Ginger, Black, White
    }
}

fun main() {
    val inputString = classpath("/demo5/cat-with-id.json").readText()

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())
    val parsedCat = jsonMapper.readValue(inputString, Cat::class.java)

    println(parsedCat) // Cat(Name=Marmalade, ID=0e8f548a-e792-4b8a-aeba-8fc06ac810a3, Age=3, Weight=10.4, Colour=Ginger)
}
