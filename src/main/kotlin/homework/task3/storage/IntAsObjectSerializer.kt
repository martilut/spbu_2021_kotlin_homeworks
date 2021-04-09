package homework.task3.storage

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IntAsObjectSerializer : KSerializer<Int> {

    @Serializable
    @SerialName("Int")
    data class IntSurrogate(val value: Int)

    override val descriptor: SerialDescriptor = IntSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Int) {
        IntSurrogate.serializer().serialize(encoder, IntSurrogate(value))
    }

    override fun deserialize(decoder: Decoder): Int {
        return decoder.decodeSerializableValue(IntSurrogate.serializer()).value
    }
}
