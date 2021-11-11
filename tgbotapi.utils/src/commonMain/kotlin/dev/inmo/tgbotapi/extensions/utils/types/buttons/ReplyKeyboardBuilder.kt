package dev.inmo.tgbotapi.extensions.utils.types.buttons

import dev.inmo.tgbotapi.types.buttons.*
import dev.inmo.tgbotapi.types.inputFieldPlaceholderField
import dev.inmo.tgbotapi.utils.MatrixBuilder
import dev.inmo.tgbotapi.utils.RowBuilder
import kotlinx.serialization.SerialName

/**
 * Core DSL part of Keyboard DSL. Can accept only [KeyboardButton] and returns ready to use
 * [ReplyKeyboardMarkup] via [build] method
 *
 * @see replyKeyboard
 * @see ReplyKeyboardBuilder.row
 * @see ReplyKeyboardRowBuilder
 */
class ReplyKeyboardBuilder : MatrixBuilder<KeyboardButton>() {
    /**
     * Creates [InlineKeyboardMarkup] using internal [matrix]
     */
    fun build(
        resizeKeyboard: Boolean? = null,
        oneTimeKeyboard: Boolean? = null,
        inputFieldPlaceholder: String? = null,
        selective: Boolean? = null,
    ) = ReplyKeyboardMarkup(matrix, resizeKeyboard, oneTimeKeyboard, inputFieldPlaceholder, selective)
}

/**
 * Row builder of [KeyboardButton]
 *
 * @see replyKeyboard
 * @see ReplyKeyboardBuilder.row
 */
class ReplyKeyboardRowBuilder : RowBuilder<KeyboardButton>()

/**
 * Factory-function for [ReplyKeyboardBuilder]. It will [apply] [block] to internally created [ReplyKeyboardMarkup]
 * and [ReplyKeyboardBuilder.build] [ReplyKeyboardMarkup] then
 *
 * @see ReplyKeyboardBuilder.row
 */
inline fun replyKeyboard(
    resizeKeyboard: Boolean? = null,
    oneTimeKeyboard: Boolean? = null,
    inputFieldPlaceholder: String? = null,
    selective: Boolean? = null,
    crossinline block: ReplyKeyboardBuilder.() -> Unit
) = ReplyKeyboardBuilder().apply(block).build(resizeKeyboard, oneTimeKeyboard, inputFieldPlaceholder, selective)

/**
 * Creates an [ReplyKeyboardRowBuilder] and [apply] [block] with this builder
 *
 * @see simpleButton
 * @see requestContactButton
 * @see requestLocationButton
 * @see requestPollButton
 */
inline fun ReplyKeyboardBuilder.row(
    crossinline block: ReplyKeyboardRowBuilder.() -> Unit
) = add(ReplyKeyboardRowBuilder().apply(block).row)

/**
 * Creates and put [SimpleKeyboardButton]
 *
 * @see replyKeyboard
 * @see ReplyKeyboardBuilder.row
 */
inline fun ReplyKeyboardRowBuilder.simpleButton(
    text: String
) = add(SimpleKeyboardButton(text))

/**
 * Creates and put [RequestContactKeyboardButton]
 *
 * @see replyKeyboard
 * @see ReplyKeyboardBuilder.row
 */
inline fun ReplyKeyboardRowBuilder.requestContactButton(
    text: String
) = add(RequestContactKeyboardButton(text))

/**
 * Creates and put [RequestLocationKeyboardButton]
 *
 * @see replyKeyboard
 * @see ReplyKeyboardBuilder.row
 */
inline fun ReplyKeyboardRowBuilder.requestLocationButton(
    text: String
) = add(RequestLocationKeyboardButton(text))

/**
 * Creates and put [RequestPollKeyboardButton]
 *
 * @see replyKeyboard
 * @see ReplyKeyboardBuilder.row
 */
inline fun ReplyKeyboardRowBuilder.requestPollButton(
    text: String,
    pollType: KeyboardButtonPollType
) = add(RequestPollKeyboardButton(text, pollType))