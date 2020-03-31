package com.github.insanusmokrassar.TelegramBotAPI.extensions.api

import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.GetUpdates
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.update.abstracts.Update

suspend fun RequestsExecutor.getUpdates(
    offset: UpdateIdentifier? = null,
    limit: Int = getUpdatesLimit.last,
    timeout: Seconds? = null,
    allowed_updates: List<String>? = ALL_UPDATES_LIST
) = execute(
    GetUpdates(
        offset, limit, timeout, allowed_updates
    )
)

suspend fun RequestsExecutor.getUpdates(
    lastUpdate: Update,
    limit: Int = getUpdatesLimit.last,
    timeout: Seconds? = null,
    allowed_updates: List<String>? = ALL_UPDATES_LIST
) = getUpdates(
    lastUpdate.updateId + 1, limit, timeout, allowed_updates
)
