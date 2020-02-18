package io.github.alexswilliams.serialisation.kotlinx.demo6serialisation

import kotlinx.serialization.*
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.protobuf.ProtoBuf
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
    val cat = Cat(Name = "Fluffy", ID = UUID.randomUUID(), Age = 6, Weight = 6.9f, Colour = Cat.CatColour.White)

    val jsonMapper = Json(JsonConfiguration.Stable)
    val asJson = jsonMapper.stringify(Cat.serializer(), cat)
    println(asJson)
    // {"Name":"Fluffy",
    //  "ID":"52bc9bc6-a193-411e-a1b8-f293eec794dd",
    //  "Age":6,"Weight":6.9,"Colour":"White"}


    val protoBufMapper = ProtoBuf()
    val asProtoBuf = protoBufMapper.dump(Cat.serializer(), cat)
    println(asProtoBuf.toList())
    // [10, 6, 70, 108, 117, 102, 102, 121, 18, 36, 102, 100, 55, 49, 49, 53, 49, 99,
    //  45, 56, 98, 100, 50, 45, 52, 56, 98, 54, 45, 57, 48, 101, 98, 45, 50, 48, 48,
    //  102, 57, 57, 53, 99, 51, 100, 102, 98, 24, 6, 37, -51, -52, -36, 64, 40, 2]


    val cborMapper = Cbor()
    val asCbor = cborMapper.dump(Cat.serializer(), cat)
    println(asCbor.toList())
    // [-65, 100, 78, 97, 109, 101, 102, 70, 108, 117, 102, 102, 121, 98, 73, 68, 120,
    //  36, 102, 100, 55, 49, 49, 53, 49, 99, 45, 56, 98, 100, 50, 45, 52, 56, 98, 54,
    //  45, 57, 48, 101, 98, 45, 50, 48, 48, 102, 57, 57, 53, 99, 51, 100, 102, 98, 99,
    //  65, 103, 101, 6, 102, 87, 101, 105, 103, 104, 116, -6, 64, -36, -52, -51, 102,
    //  67, 111, 108, 111, 117, 114, 101, 87, 104, 105, 116, 101, -1]
}
