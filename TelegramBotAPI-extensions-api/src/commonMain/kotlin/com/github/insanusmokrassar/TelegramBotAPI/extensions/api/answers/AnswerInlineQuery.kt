package com.github.insanusmokrassar.TelegramBotAPI.extensions.api.answers

import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.answers.AnswerInlineQuery
import com.github.insanusmokrassar.TelegramBotAPI.types.InlineQueries.InlineQueryResult.abstracts.InlineQueryResult
import com.github.insanusmokrassar.TelegramBotAPI.types.InlineQueries.abstracts.InlineQuery
import com.github.insanusmokrassar.TelegramBotAPI.types.InlineQueryIdentifier

suspend fun RequestsExecutor.answerInlineQuery(
    inlineQueryID: InlineQueryIdentifier,
    results: List<InlineQueryResult> = emptyList(),
    cachedTime: Int? = null,
    isPersonal: Boolean? = null,
    nextOffset: String? = null,
    switchPmText: String? = null,
    switchPmParameter: String? = null
) = execute(
    AnswerInlineQuery(inlineQueryID, results, cachedTime, isPersonal, nextOffset, switchPmText, switchPmParameter)
)

suspend fun RequestsExecutor.answerInlineQuery(
    inlineQuery: InlineQuery,
    results: List<InlineQueryResult> = emptyList(),
    cachedTime: Int? = null,
    isPersonal: Boolean? = null,
    nextOffset: String? = null,
    switchPmText: String? = null,
    switchPmParameter: String? = null
) = answerInlineQuery(inlineQuery.id, results, cachedTime, isPersonal, nextOffset, switchPmText, switchPmParameter)
