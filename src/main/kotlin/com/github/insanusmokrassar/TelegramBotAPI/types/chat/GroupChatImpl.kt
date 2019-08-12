package com.github.insanusmokrassar.TelegramBotAPI.types.chat

import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.abstracts.GroupChat
import com.github.insanusmokrassar.TelegramBotAPI.types.message.RawMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupChatImpl(
    @SerialName(idField)
    override val id: ChatId,
    @SerialName(titleField)
    override val title: String
) : GroupChat
