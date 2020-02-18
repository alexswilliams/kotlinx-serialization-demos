package io.github.alexswilliams.serialisation.jackson.demo4bjunklists

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.alexswilliams.serialisation.classpath

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

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())

    // These still work and return exactly the same thing
    val parsedCats_Erased = jsonMapper.readValue(inputString, List::class.java)
    println(parsedCats_Erased)
    // [{ID=8e97a266-1722-41f1-8e93-d020c9530f90, Name=Marmalade, Age=3, Weight=10.4, Owner={ID=d3d5a48f-7c3b-4bd0-99c9-f864c189065d, Name=Alex, Address={First Line=123 Kitten Street, City=Leeds}}, Personality=Snooty, Colour=Ginger},
    //  {ID=a8fcf263-e998-4682-92f3-8c5b9a2e0ef4, Name=Paprika, Age=6, Weight=12.2, Personality=Useless, Colour=Brown},
    //  {ID=8d6f30ef-bd5d-475a-856c-fefe1f0b90b7, Name=Mr MistoffeLeeds, Age=2, Weight=7.1, Personality=Zippy, Colour=Black, Owner={ID=633c1038-19f7-4604-9315-ec300f11243f, Name=Sarah, Address={First Line=11 Meow Street, City=Leeds}}}]

    val parsedCats_Cast = jsonMapper.readValue(inputString, List::class.java) as List<Cat>
    println(parsedCats_Cast) // As above

    // These, however, don't work:
    val parsedCats_Array = jsonMapper.readValue(inputString, Array<Cat>::class.java).toList()
    // throws: Unrecognized field "ID"
    println(parsedCats_Array)

    // If we disable failure on unknown fields, it moves onto the other issues:
    jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    val parsedCats_TypeRef = jsonMapper.readValue(inputString, object : TypeReference<List<Cat>>() {})
    // throws: String "Brown": not one of the values accepted for Enum class: [Ginger, White, Black]
    println(parsedCats_TypeRef)

    val parsedCats_JavaTypeHint = jsonMapper.readValue<List<Cat>>(
        inputString,
        jsonMapper.typeFactory.constructCollectionType(List::class.java, Cat::class.java)
    )
    println(parsedCats_JavaTypeHint) // As above
}
