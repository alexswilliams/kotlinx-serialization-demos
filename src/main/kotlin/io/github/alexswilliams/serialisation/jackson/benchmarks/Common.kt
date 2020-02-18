package io.github.alexswilliams.serialisation.jackson.benchmarks

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
