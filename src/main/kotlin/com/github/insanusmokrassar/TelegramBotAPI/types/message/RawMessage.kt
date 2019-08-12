package com.github.insanusmokrassar.TelegramBotAPI.types.message

import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.MessageEntity.RawMessageEntities
import com.github.insanusmokrassar.TelegramBotAPI.types.MessageEntity.RawMessageEntitiesSerializer
import com.github.insanusmokrassar.TelegramBotAPI.types.buttons.InlineKeyboardMarkup
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.*
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.abstracts.*
import com.github.insanusmokrassar.TelegramBotAPI.types.files.*
import com.github.insanusmokrassar.TelegramBotAPI.types.games.Game
import com.github.insanusmokrassar.TelegramBotAPI.types.message.ChatEvents.*
import com.github.insanusmokrassar.TelegramBotAPI.types.message.ChatEvents.abstracts.*
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.Message
import com.github.insanusmokrassar.TelegramBotAPI.types.message.content.*
import com.github.insanusmokrassar.TelegramBotAPI.types.message.content.abstracts.MessageContent
import com.github.insanusmokrassar.TelegramBotAPI.types.message.content.media.*
import com.github.insanusmokrassar.TelegramBotAPI.types.message.payments.InvoiceOfPayment
import com.github.insanusmokrassar.TelegramBotAPI.types.message.payments.SuccessfulPaymentInfo
import com.github.insanusmokrassar.TelegramBotAPI.types.message.payments.abstracts.PaymentInfo
import com.github.insanusmokrassar.TelegramBotAPI.types.payments.Invoice
import com.github.insanusmokrassar.TelegramBotAPI.types.payments.SuccessfulPayment
import com.github.insanusmokrassar.TelegramBotAPI.types.polls.Poll
import kotlinx.serialization.*
import kotlin.reflect.KClass

// TODO:: add PassportData type
@Serializable
data class RawMessage(
    @SerialName(messageIdField)
    val messageId: MessageIdentifier,
    @SerialName(dateField)
    val date: TelegramDate,
    @SerialName(chatField)
    private val chat: Chat,
    @SerialName(fromField)
    private val from: User? = null,
    private val forward_from: User? = null,
    private val forward_from_chat: Chat? = null,
    private val forward_from_message_id: MessageIdentifier? = null,
    private val forward_signature: ForwardSignature? = null,
    private val forward_sender_name: ForwardSenderName? = null,
    private val forward_date: TelegramDate? = null,
    private val reply_to_message: RawMessage? = null,
    private val edit_date: TelegramDate? = null,
    private val media_group_id: MediaGroupIdentifier? = null,
    private val author_signature: AuthorSignature? = null,
    private val text: String? = null,
    @Serializable(RawMessageEntitiesSerializer::class)
    private val entities: RawMessageEntities? = null,
    private val caption: String? = null,
    @Serializable(RawMessageEntitiesSerializer::class)
    private val caption_entities: RawMessageEntities? = null,
    private val audio: AudioFile? = null,
    private val document: DocumentFile? = null,
    private val animation: AnimationFile? = null,
    private val game: Game? = null,
    @Serializable(PhotoSerializer::class)
    private val photo: Photo? = null,
    private val sticker: Sticker? = null,
    private val video: VideoFile? = null,
    private val voice: VoiceFile? = null,
    private val video_note: VideoNoteFile? = null,
    private val contact: Contact? = null,
    private val location: Location? = null,
    private val venue: Venue? = null,
    private val poll: Poll? = null,
    private val new_chat_members: List<User>? = null,
    private val left_chat_member: User? = null,
    private val new_chat_title: String? = null,
    @Serializable(PhotoSerializer::class)
    private val new_chat_photo: Photo? = null,
    private val delete_chat_photo: Boolean = false,
    private val group_chat_created: Boolean = false,
    private val supergroup_chat_created: Boolean = false,
    private val channel_chat_created: Boolean = false,
    private val migrate_to_chat_id: ChatIdentifier? = null,
    private val migrate_from_chat_id: ChatIdentifier? = null,
    private val pinned_message: RawMessage? = null,
    private val invoice: Invoice? = null,
    private val successful_payment: SuccessfulPayment? = null,

    // login property
    private val connected_website: String? = null,

    // passport property
    private val passport_data: Unit? = null,

    private val reply_markup: InlineKeyboardMarkup? = null
) {
    @Transient
    private val content: MessageContent? by lazy {
        val adaptedCaptionEntities = caption ?.let {
            caption_entities ?.map {
                it.asMessageEntity(caption)
            }
        } ?: emptyList()

        when {
            text != null -> TextContent(text, entities ?.map { it.asMessageEntity(text) } ?: emptyList())
            audio != null -> AudioContent(
                audio,
                caption,
                adaptedCaptionEntities
            )
            video != null -> VideoContent(
                video,
                caption,
                adaptedCaptionEntities
            )
            animation != null -> AnimationContent(
                animation,
                document,
                caption,
                adaptedCaptionEntities
            )
            document != null -> DocumentContent(
                document,
                caption,
                adaptedCaptionEntities
            )
            voice != null -> VoiceContent(
                voice,
                caption,
                adaptedCaptionEntities
            )
            photo != null -> PhotoContent(
                photo.toList(),
                caption,
                adaptedCaptionEntities
            )
            sticker != null -> StickerContent(sticker)
            game != null -> GameContent(game)
            video_note != null -> VideoNoteContent(video_note)
            contact != null -> ContactContent(contact)
            location != null -> LocationContent(location)
            venue != null -> VenueContent(venue)
            poll != null -> PollContent(poll)
            else -> null
        }
    }

    @Transient
    private val forwarded: ForwardedMessage? by lazy {
        forward_date ?: return@lazy null // According to the documentation, now any forwarded message contains this field
        when {
            forward_sender_name != null -> AnonymousForwardedMessage(
                forward_date,
                forward_sender_name
            )
            forward_from_chat != null -> ForwardedFromChannelMessage(
                forward_date,
                forward_from_message_id ?: throw IllegalStateException("Channel forwarded message must contain message id, but was not"),
                forward_from_chat,
                forward_signature
            )
            forward_from != null -> UserForwardedMessage(
                forward_date,
                forward_from
            )
            else -> null
        }
    }

    @Transient
    private val chatEvent: ChatEvent? by lazy {
        when {
            new_chat_members != null -> NewChatMembers(new_chat_members.toList())
            left_chat_member != null -> LeftChatMember(left_chat_member)
            new_chat_title != null -> NewChatTitle(new_chat_title)
            new_chat_photo != null -> NewChatPhoto(new_chat_photo.toList())
            delete_chat_photo -> DeleteChatPhoto()
            group_chat_created -> GroupChatCreated(
                migrate_to_chat_id
            )
            supergroup_chat_created -> SupergroupChatCreated(
                migrate_from_chat_id
            )
            channel_chat_created -> ChannelChatCreated()
            pinned_message != null -> PinnedMessage(pinned_message.asMessage)
            else -> null
        }
    }

    @Transient
    private val paymentInfo: PaymentInfo? by lazy {
        when {
            invoice != null -> InvoiceOfPayment(invoice)
            successful_payment != null -> SuccessfulPaymentInfo(successful_payment)
            else -> null
        }
    }



    @Transient
    val asMessage: Message by lazy {
        chatEvent ?.let {
            chatEvent ->
            when (chat) {
                is SupergroupChat -> SupergroupEventMessage(
                    messageId,
                    chat,
                    chatEvent as? SupergroupEvent ?: throwWrongChatEvent(SupergroupEvent::class, chatEvent),
                    date.asDate
                )
                is GroupChat -> GroupEventMessage(
                    messageId,
                    chat,
                    chatEvent as? GroupEvent ?: throwWrongChatEvent(GroupChat::class, chatEvent),
                    date.asDate
                )
                is ChannelChat -> ChannelEventMessage(
                    messageId,
                    chat,
                    chatEvent as? ChannelEvent ?: throwWrongChatEvent(ChannelEvent::class, chatEvent),
                    date.asDate
                )
                else -> throw IllegalStateException("Expected one of the public chats, but was $chat (in extracting of chat event message)")
            }
        } ?: content ?.let {
            content ->
            media_group_id ?.let {
                when (from) {
                    null -> ChannelMediaGroupMessage(
                        messageId,
                        chat,
                        date.asDate,
                        it,
                        when (content) {
                            is PhotoContent -> content
                            is VideoContent -> content
                            else -> throw IllegalStateException("Unsupported content for media group")
                        },
                        edit_date ?.asDate,
                        forwarded,
                        reply_to_message ?.asMessage,
                        reply_markup
                    )
                    else -> CommonMediaGroupMessage(
                        messageId,
                        from,
                        chat,
                        date.asDate,
                        it,
                        when (content) {
                            is PhotoContent -> content
                            is VideoContent -> content
                            else -> throw IllegalStateException("Unsupported content for media group")
                        },
                        edit_date ?.asDate,
                        forwarded,
                        reply_to_message ?.asMessage,
                        reply_markup
                    )
                }
            } ?: when (chat) {
                is ChannelChat -> ChannelMessage(
                    messageId,
                    chat,
                    content,
                    date.asDate,
                    edit_date ?.asDate,
                    forwarded,
                    reply_to_message ?.asMessage,
                    reply_markup,
                    author_signature
                )
                else -> CommonMessageImpl(
                    messageId,
                    from ?: throw IllegalStateException("Was detected common message, but owner (sender) of the message was not found"),
                    chat,
                    content,
                    date.asDate,
                    edit_date ?.asDate,
                    forwarded,
                    reply_to_message ?.asMessage,
                    reply_markup,
                    paymentInfo
                )
            }
        } ?: throw IllegalStateException("Was not found supported type of data")
    }

    private fun throwWrongChatEvent(expected: KClass<*>, but: ChatEvent): CommonEvent {
        throw IllegalStateException("Wrong type of chat event: expected $expected, but was $but")
    }
}
