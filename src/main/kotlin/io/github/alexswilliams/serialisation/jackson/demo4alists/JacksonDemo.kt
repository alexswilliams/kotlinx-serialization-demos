package io.github.alexswilliams.serialisation.jackson.demo4alists

import com.fasterxml.jackson.core.type.TypeReference
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
    val inputString = classpath("/demo4/many-cats.json").readText()

    val jsonMapper = ObjectMapper().registerModule(KotlinModule())

    val parsedCats_Erased = jsonMapper.readValue(inputString, List::class.java)
    println(parsedCats_Erased)
    // [{Name=Marmalade, Age=3, Weight=10.4, Colour=Ginger},
    //  {Name=Paprika, Age=6, Weight=12.2, Colour=Ginger},
    //  {Name=Mr MistoffeLeeds, Age=2, Weight=7.1, Colour=Black}]

    val parsedCats_Cast = jsonMapper.readValue(inputString, List::class.java) as List<Cat>
    println(parsedCats_Cast) // As above

    val parsedCats_Array = jsonMapper.readValue(inputString, Array<Cat>::class.java).toList()
    println(parsedCats_Array)
    // [Cat(Name=Marmalade, Age=3, Weight=10.4, Colour=Ginger),
    //  Cat(Name=Paprika, Age=6, Weight=12.2, Colour=Ginger),
    //  Cat(Name=Mr MistoffeLeeds, Age=2, Weight=7.1, Colour=Black)]

    val parsedCats_TypeRef = jsonMapper.readValue(inputString, object : TypeReference<List<Cat>>() {})
    println(parsedCats_TypeRef) // As above

    val parsedCats_JavaTypeHint = jsonMapper.readValue<List<Cat>>(
        inputString,
        jsonMapper.typeFactory.constructCollectionType(List::class.java, Cat::class.java)
    )
    println(parsedCats_JavaTypeHint) // As above
}
