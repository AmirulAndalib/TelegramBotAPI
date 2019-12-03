package com.github.insanusmokrassar.TelegramBotAPI.types.MessageEntity

import com.github.insanusmokrassar.TelegramBotAPI.utils.commandHTML
import com.github.insanusmokrassar.TelegramBotAPI.utils.commandMarkdown

private val commandRegex = Regex("[/!][^@\\s]*")

data class BotCommandMessageEntity(
    override val offset: Int,
    override val length: Int,
    override val sourceString: String
) : MessageEntity {
    override val asMarkdownSource: String = sourceString.commandMarkdown()
    override val asHtmlSource: String = sourceString.commandHTML()

    val command: String by lazy {
        commandRegex.find(sourceString) ?.value ?.substring(1) ?: sourceString.substring(1)// skip first symbol like "/" or "!"
    }
}
