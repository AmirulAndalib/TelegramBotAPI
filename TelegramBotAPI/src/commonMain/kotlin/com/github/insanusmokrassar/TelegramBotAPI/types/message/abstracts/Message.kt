package com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts

import com.github.insanusmokrassar.TelegramBotAPI.types.MessageIdentifier
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.abstracts.Chat
import com.github.insanusmokrassar.TelegramBotAPI.types.message.RawMessage
import com.soywiz.klock.DateTime
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

interface Message {
    val messageId: MessageIdentifier
    val chat: Chat
    val date: DateTime
}

data class UnknownMessageType(
    override val messageId: MessageIdentifier,
    override val chat: Chat,
    override val date: DateTime,
    val insideException: Exception
) : Message

internal class TelegramBotAPIMessageDeserializationStrategyClass<T> : DeserializationStrategy<T> {
    override val descriptor: SerialDescriptor = SerialDescriptor("TelegramBotAPIMessageSerializer", PolymorphicKind.OPEN)

    override fun patch(decoder: Decoder, old: T): T = throw UpdateNotSupportedException("TelegramBotAPIMessageSerializer")
    override fun deserialize(decoder: Decoder): T {
        return RawMessage.serializer().deserialize(decoder).asMessage as T
    }
}
internal object TelegramBotAPIMessageDeserializationStrategy
    : DeserializationStrategy<Message> by TelegramBotAPIMessageDeserializationStrategyClass()

internal class TelegramBotAPIMessageDeserializeOnlySerializerClass<T : Message> : KSerializer<T> {
    private val deserializer = TelegramBotAPIMessageDeserializationStrategyClass<T>()
    override val descriptor: SerialDescriptor
        get() = deserializer.descriptor

    override fun deserialize(decoder: Decoder): T {
        return deserializer.deserialize(decoder)
    }

    override fun serialize(encoder: Encoder, value: T) {
        throw UnsupportedOperationException("Currently, Message objects can't be serialized y this serializer")
    }
}
internal object TelegramBotAPIMessageDeserializeOnlySerializer
    : KSerializer<Message> by TelegramBotAPIMessageDeserializeOnlySerializerClass()
