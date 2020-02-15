package com.github.insanusmokrassar.TelegramBotAPI.extensions.api.chat.stickers

import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.chat.stickers.SetChatStickerSet
import com.github.insanusmokrassar.TelegramBotAPI.types.ChatIdentifier
import com.github.insanusmokrassar.TelegramBotAPI.types.StickerSetName
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.abstracts.SupergroupChat

suspend fun RequestsExecutor.setChatStickerSet(
    chatId: ChatIdentifier,
    stickerSetName: StickerSetName
) = execute(SetChatStickerSet(chatId, stickerSetName))

suspend fun RequestsExecutor.setChatStickerSet(
    chat: SupergroupChat,
    stickerSetName: StickerSetName
) = setChatStickerSet(chat.id, stickerSetName)
