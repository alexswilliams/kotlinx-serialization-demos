package io.github.alexswilliams.serialisation.kotlinx.benchmarks

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
