package com.github.insanusmokrassar.TelegramBotAPI.types.InlineQueries.InlineQueryResult.serializers

import com.github.insanusmokrassar.TelegramBotAPI.types.InlineQueries.InlineQueryResult.*
import com.github.insanusmokrassar.TelegramBotAPI.types.InlineQueries.InlineQueryResult.abstracts.InlineQueryResult
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializer(InlineQueryResult::class)
internal object InlineQueryResultSerializer : KSerializer<InlineQueryResult> {
    override val descriptor: SerialDescriptor = SerialDescriptor(InlineQueryResult::class.toString(), PolymorphicKind.OPEN)
    override fun serialize(encoder: Encoder, value: InlineQueryResult) {
        when(value) {
            is InlineQueryResultArticle -> InlineQueryResultArticle.serializer().serialize(encoder, value)
            is InlineQueryResultAudioCachedImpl -> InlineQueryResultAudioCachedImpl.serializer().serialize(encoder, value)
            is InlineQueryResultAudioImpl -> InlineQueryResultAudioImpl.serializer().serialize(encoder, value)
            is InlineQueryResultContact -> InlineQueryResultContact.serializer().serialize(encoder, value)
            is InlineQueryResultDocumentCachedImpl -> InlineQueryResultDocumentCachedImpl.serializer().serialize(encoder, value)
            is InlineQueryResultDocumentImpl -> InlineQueryResultDocumentImpl.serializer().serialize(encoder, value)
            is InlineQueryResultGame -> InlineQueryResultGame.serializer().serialize(encoder, value)
            is InlineQueryResultGifCachedImpl -> InlineQueryResultGifCachedImpl.serializer().serialize(encoder, value)
            is InlineQueryResultGifImpl -> InlineQueryResultGifImpl.serializer().serialize(encoder, value)
            is InlineQueryResultLocation -> InlineQueryResultLocation.serializer().serialize(encoder, value)
            is InlineQueryResultMpeg4GifCachedImpl -> InlineQueryResultMpeg4GifCachedImpl.serializer().serialize(encoder, value)
            is InlineQueryResultMpeg4GifImpl -> InlineQueryResultMpeg4GifImpl.serializer().serialize(encoder, value)
            is InlineQueryResultPhotoCachedImpl -> InlineQueryResultPhotoCachedImpl.serializer().serialize(encoder, value)
            is InlineQueryResultPhotoImpl -> InlineQueryResultPhotoImpl.serializer().serialize(encoder, value)
            is InlineQueryResultStickerCached -> InlineQueryResultStickerCached.serializer().serialize(encoder, value)
            is InlineQueryResultVenue -> InlineQueryResultVenue.serializer().serialize(encoder, value)
            is InlineQueryResultVideoCachedImpl -> InlineQueryResultVideoCachedImpl.serializer().serialize(encoder, value)
            is InlineQueryResultVideoImpl -> InlineQueryResultVideoImpl.serializer().serialize(encoder, value)
            is InlineQueryResultVoiceCachedImpl -> InlineQueryResultVoiceCachedImpl.serializer().serialize(encoder, value)
            is InlineQueryResultVoiceImpl -> InlineQueryResultVoiceImpl.serializer().serialize(encoder, value)
        }
    }

    override fun deserialize(decoder: Decoder): InlineQueryResult {
        throw TODO()
    }
}