package com.github.insanusmokrassar.TelegramBotAPI.requests.chat.modify

import com.github.insanusmokrassar.TelegramBotAPI.CommonAbstracts.types.*
import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.SimpleRequest
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.abstracts.PublicChat
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.Message
import kotlinx.serialization.*
import kotlinx.serialization.internal.BooleanSerializer

@Serializable
data class PinChatMessage (
    @SerialName(chatIdField)
    override val chatId: ChatIdentifier,
    @SerialName(messageIdField)
    override val messageId: MessageIdentifier,
    @SerialName(disableNotificationField)
    override val disableNotification: Boolean = false
): ChatRequest, SimpleRequest<Boolean>, MessageAction, DisableNotification {
    override fun method(): String = "pinChatMessage"
    override val resultDeserializer: DeserializationStrategy<Boolean>
        get() = BooleanSerializer
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}
