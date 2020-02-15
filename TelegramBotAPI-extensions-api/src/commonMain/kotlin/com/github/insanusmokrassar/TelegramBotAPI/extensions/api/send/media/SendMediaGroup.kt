package com.github.insanusmokrassar.TelegramBotAPI.extensions.api.send.media

import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.send.media.SendMediaGroup
import com.github.insanusmokrassar.TelegramBotAPI.types.ChatIdentifier
import com.github.insanusmokrassar.TelegramBotAPI.types.InputMedia.MediaGroupMemberInputMedia
import com.github.insanusmokrassar.TelegramBotAPI.types.MessageIdentifier
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.abstracts.Chat

suspend fun RequestsExecutor.sendMediaGroup(
    chatId: ChatIdentifier,
    media: List<MediaGroupMemberInputMedia>,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null
) = execute(
    SendMediaGroup(
        chatId, media, disableNotification, replyToMessageId
    )
)

suspend fun RequestsExecutor.sendMediaGroup(
    chat: Chat,
    media: List<MediaGroupMemberInputMedia>,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null
) = sendMediaGroup(
    chat.id, media, disableNotification, replyToMessageId
)
