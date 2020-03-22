package com.github.insanusmokrassar.TelegramBotAPI.requests.answers

import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.SimpleRequest
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.CallbackQuery.CallbackQuery
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer

@Serializable
data class AnswerCallbackQuery(
    @SerialName(callbackQueryIdField)
    val callbackQueryId: CallbackQueryIdentifier,
    @SerialName(textField)
    val text: String? = null,
    @SerialName(showAlertField)
    val showAlert: Boolean? = null,
    @SerialName(urlField)
    val url: String? = null,
    @SerialName(cachedTimeField)
    val cachedTimeSeconds: Int? = null
) : SimpleRequest<Boolean> {
    override fun method(): String = "answerCallbackQuery"
    override val resultDeserializer: DeserializationStrategy<Boolean>
        get() = Boolean.serializer()
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}

fun CallbackQuery.createAnswer(
    text: String? = null,
    showAlert: Boolean? = null,
    url: String? = null,
    cachedTimeSeconds: Int? = null
): AnswerCallbackQuery = AnswerCallbackQuery(id, text, showAlert, url, cachedTimeSeconds)
