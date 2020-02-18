package io.github.alexswilliams.serialisation.jackson.demo6serialisation

import com.fasterxml.jackson.databind.ObjectMapper
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
    val cat = Cat(Name = "Fluffy", ID = UUID.randomUUID(), Age = 6, Weight = 6.9f, Colour = Cat.CatColour.White)

    val jsonMapper = ObjectMapper()
    val asJson = jsonMapper.writeValueAsString(cat)

    println(asJson)
    // {"name":"Fluffy","age":6,"weight":6.9,
    //  "colour":"White","id":"bc1d3823-8618-4000-8b1e-041d5b6e14af"}
}
