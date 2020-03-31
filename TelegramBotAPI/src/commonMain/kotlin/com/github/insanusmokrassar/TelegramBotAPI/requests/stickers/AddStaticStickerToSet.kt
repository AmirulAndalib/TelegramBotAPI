package com.github.insanusmokrassar.TelegramBotAPI.requests.stickers

import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.*
import com.github.insanusmokrassar.TelegramBotAPI.requests.common.CommonMultipartFileRequest
import com.github.insanusmokrassar.TelegramBotAPI.requests.stickers.abstracts.StandardStickerSetAction
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.stickers.MaskPosition
import kotlinx.serialization.*

fun AddStaticStickerToSet(
    userId: UserId,
    stickerSetName: String,
    sticker: InputFile,
    emojis: String,
    maskPosition: MaskPosition? = null
): Request<Boolean> {
    val data = AddStaticStickerToSet(userId, stickerSetName, emojis, sticker as? FileId, maskPosition)
    return when (sticker) {
        is MultipartFile -> CommonMultipartFileRequest(
            data,
            mapOf(pngStickerField to sticker)
        )
        is FileId -> data
    }
}

@Deprecated(
    "Renamed",
    ReplaceWith("AddStaticStickerToSet", "com.github.insanusmokrassar.TelegramBotAPI.requests.stickers.AddStaticStickerToSet")
)
fun AddStickerToSet(
    userId: UserId,
    stickerSetName: String,
    sticker: InputFile,
    emojis: String,
    maskPosition: MaskPosition? = null
) = AddStaticStickerToSet(userId, stickerSetName, sticker, emojis, maskPosition)

@Deprecated(
    "Renamed",
    ReplaceWith("AddStaticStickerToSet", "com.github.insanusmokrassar.TelegramBotAPI.requests.stickers.AddStaticStickerToSet")
)
typealias AddStickerToSet = AddStaticStickerToSet

@Serializable
data class AddStaticStickerToSet internal constructor(
    @SerialName(userIdField)
    override val userId: UserId,
    @SerialName(nameField)
    override val name: String,
    @SerialName(emojisField)
    override val emojis: String,
    @SerialName(pngStickerField)
    val sticker: FileId? = null,
    @SerialName(maskPositionField)
    override val maskPosition: MaskPosition? = null
) : StandardStickerSetAction {
    init {
        if(emojis.isEmpty()) {
            throw IllegalArgumentException("Emojis must not be empty")
        }
    }

    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()

    override fun method(): String = "addStickerToSet"
}
