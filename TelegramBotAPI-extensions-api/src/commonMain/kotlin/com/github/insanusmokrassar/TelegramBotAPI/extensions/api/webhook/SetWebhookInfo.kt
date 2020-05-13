package com.github.insanusmokrassar.TelegramBotAPI.extensions.api.webhook

import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.FileId
import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.MultipartFile
import com.github.insanusmokrassar.TelegramBotAPI.requests.webhook.SetWebhook

/**
 * Use this method to send information about webhook (like [url] and [certificate])
 */
suspend fun RequestsExecutor.setWebhookInfo(
    url: String,
    certificate: FileId,
    maxAllowedConnections: Int? = null,
    allowedUpdates: List<String>? = null
) = execute(
    SetWebhook(
        url, certificate, maxAllowedConnections, allowedUpdates
    )
)

/**
 * Use this method to send information about webhook (like [url] and [certificate])
 */
suspend fun RequestsExecutor.setWebhookInfo(
    url: String,
    certificate: MultipartFile,
    maxAllowedConnections: Int? = null,
    allowedUpdates: List<String>? = null
) = execute(
    SetWebhook(
        url, certificate, maxAllowedConnections, allowedUpdates
    )
)
