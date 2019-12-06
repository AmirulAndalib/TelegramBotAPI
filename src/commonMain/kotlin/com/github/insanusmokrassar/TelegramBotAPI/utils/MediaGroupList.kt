package com.github.insanusmokrassar.TelegramBotAPI.utils

import com.github.insanusmokrassar.TelegramBotAPI.types.MediaGroupIdentifier
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.abstracts.Chat
import com.github.insanusmokrassar.TelegramBotAPI.types.message.ForwardedMessage
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.*
import com.github.insanusmokrassar.TelegramBotAPI.types.update.abstracts.BaseMessageUpdate

val List<BaseMessageUpdate>.forwarded: ForwardedMessage?
    get() = first().let {
        (it as? AbleToBeForwardedMessage) ?.forwarded
    }

val List<BaseMessageUpdate>.replyTo: Message?
    get() = first().let {
        (it as? AbleToReplyMessage) ?.replyTo
    }

val List<BaseMessageUpdate>.chat: Chat?
    get() = first().data.chat

val List<BaseMessageUpdate>.mediaGroupId: MediaGroupIdentifier?
    get() = (first().data as? MediaGroupMessage) ?.mediaGroupId
