package com.github.insanusmokrassar.TelegramBotAPI.types

import kotlinx.serialization.*

@Serializable
data class ResponseParametersRaw(
    @SerialName("migrate_to_chat_id")
    private val migrateToChatId: ChatId? = null,
    @SerialName("retry_after")
    private val retryAfter: Long? = null
) {
    @Transient
    private val createTime: Long = System.currentTimeMillis()
    val error: RequestError? by lazy {
        when {
            migrateToChatId != null -> MigrateChatId(migrateToChatId);
            retryAfter != null -> RetryAfterError(retryAfter, createTime);
            else -> null
        }
    }
}
