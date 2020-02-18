package io.github.alexswilliams.serialisation.kotlinx.demo5uuids

import io.github.alexswilliams.serialisation.classpath
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.util.*

@Serializable
data class Cat(
    val Name: String,
    @Serializable(with = UUIDCustomSerializer::class) val ID: UUID,
    val Age: Int,
    val Weight: Float,
    val Colour: CatColour
) {
    enum class CatColour {
        Ginger, Black, White
    }
}

class UUIDCustomSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor get() = StringDescriptor
    override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())
    override fun serialize(encoder: Encoder, obj: UUID) = encoder.encodeString(obj.toString())
}

fun main() {
    val inputString = classpath("/demo5/cat-with-id.json").readText()

    val jsonMapper = Json(JsonConfiguration.Stable)
    val parsedCat = jsonMapper.parse(Cat.serializer(), inputString)

    println(parsedCat) // Cat(Name=Marmalade, ID=0e8f548a-e792-4b8a-aeba-8fc06ac810a3, Age=3, Weight=10.4, Colour=Ginger)
}
