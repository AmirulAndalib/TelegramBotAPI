package com.github.insanusmokrassar.TelegramBotAPI.requests.get

import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.SimpleRequest
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import kotlinx.serialization.*

@Serializable
data class GetUserProfilePhotos(
    @SerialName(userIdField)
    val userId: UserId,
    @SerialName(offsetField)
    val offset: Int? = null,
    @SerialName(limitField)
    val limit: Int? = null
): SimpleRequest<UserProfilePhotos> {
    init {
        if (offset != null && offset < 0) {
            throw IllegalArgumentException("Offset for getting user profile photos must be positive")
        }
        if (limit != null && limit !in userProfilePhotosRequestLimit) {
            throw IllegalArgumentException("Limit for getting user profile photos must be in 0 .. 100 range")
        }
    }

    override fun method(): String = "getUserProfilePhotos"
    override val resultDeserializer: DeserializationStrategy<UserProfilePhotos>
        get() = UserProfilePhotos.serializer()
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.getUserProfilePhotos(
    userId: UserId,
    offset: Int? = null,
    limit: Int? = null
) = execute(
    GetUserProfilePhotos(
        userId, offset, limit
    )
)

@Deprecated("Deprecated due to extracting into separated library")
suspend fun RequestsExecutor.getUserProfilePhotos(
    user: CommonUser,
    offset: Int? = null,
    limit: Int? = null
) = getUserProfilePhotos(user.id, offset, limit)
