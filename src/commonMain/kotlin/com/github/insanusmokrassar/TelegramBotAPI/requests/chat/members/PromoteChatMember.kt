package com.github.insanusmokrassar.TelegramBotAPI.requests.chat.members

import com.github.insanusmokrassar.TelegramBotAPI.CommonAbstracts.types.UntilDate
import com.github.insanusmokrassar.TelegramBotAPI.requests.chat.abstracts.ChatMemberRequest
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import kotlinx.serialization.*
import kotlinx.serialization.internal.BooleanSerializer

@Serializable
data class PromoteChatMember(
    @SerialName(chatIdField)
    override val chatId: ChatIdentifier,
    @SerialName(userIdField)
    override val userId: UserId,
    @SerialName(untilDateField)
    override val untilDate: TelegramDate? = null,
    @SerialName(canChangeInfoField)
    private val canChangeInfo: Boolean? = null,
    @SerialName(canPostMessagesField)
    private val canPostMessages: Boolean? = null,
    @SerialName(canEditMessagesField)
    private val canEditMessages: Boolean? = null,
    @SerialName(canDeleteMessagesField)
    private val canDeleteMessages: Boolean? = null,
    @SerialName(canInviteUsersField)
    private val canInviteUsers: Boolean? = null,
    @SerialName(canRestrictMembersField)
    private val canRestrictMembers: Boolean? = null,
    @SerialName(canPinMessagesField)
    private val canPinMessages: Boolean? = null,
    @SerialName(canPromoteMembersField)
    private val canPromoteMembers: Boolean? = null
) : ChatMemberRequest<Boolean>, UntilDate {
    override fun method(): String = "promoteChatMember"
    override val resultDeserializer: DeserializationStrategy<Boolean>
        get() = BooleanSerializer
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}
