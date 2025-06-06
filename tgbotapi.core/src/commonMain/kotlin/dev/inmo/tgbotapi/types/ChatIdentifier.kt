package dev.inmo.tgbotapi.types

import dev.inmo.micro_utils.common.Warning
import dev.inmo.tgbotapi.types.business_connection.BusinessConnectionId
import dev.inmo.tgbotapi.types.chat.User
import dev.inmo.tgbotapi.utils.RiskFeature
import dev.inmo.tgbotapi.utils.internal.ClassCastsIncluded
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlin.jvm.JvmInline

const val internalTgAppLinksBeginning = "tg://"
const val internalLinkBeginning = "https://t.me"
const val internalUserLinkBeginning = "${internalTgAppLinksBeginning}user?id="

@Serializable(ChatIdentifierSerializer::class)
@ClassCastsIncluded
sealed interface ChatIdentifier

/**
 * Also used as User Identifier
 */
@Serializable(ChatIdentifierSerializer::class)
sealed interface IdChatIdentifier : ChatIdentifier {
    abstract val chatId: RawChatId
    val threadId: MessageThreadId?
        get() = null
    val businessConnectionId: BusinessConnectionId?
        get() = null

    companion object {
        operator fun invoke(chatId: RawChatId, threadId: MessageThreadId? = null, businessConnectionId: BusinessConnectionId? = null) = threadId ?.let {
            ChatIdWithThreadId(chatId, threadId)
        } ?: businessConnectionId ?.let {
            BusinessChatId(chatId, businessConnectionId)
        } ?: ChatId(chatId)
        operator fun invoke(chatId: RawChatId, threadId: MessageThreadId) = ChatIdWithThreadId(chatId, threadId)
        operator fun invoke(chatId: RawChatId, businessConnectionId: BusinessConnectionId) = BusinessChatId(chatId, businessConnectionId)
    }
}

@Serializable(ChatIdentifierSerializer::class)
@JvmInline
value class ChatId(override val chatId: RawChatId) : IdChatIdentifier

@Serializable(ChatIdentifierSerializer::class)
@JvmInline
value class ChatIdWithThreadId(val chatIdWithThreadId: Pair<RawChatId, MessageThreadId>) : IdChatIdentifier {
    override val chatId: RawChatId
        get() = chatIdWithThreadId.first
    override val threadId: MessageThreadId
        get() = chatIdWithThreadId.second

    constructor(chatId: RawChatId, threadId: MessageThreadId): this(chatId to threadId)
}
@Serializable(ChatIdentifierSerializer::class)
@JvmInline
value class BusinessChatId(val chatIdWithBusinessConnectionId: Pair<RawChatId, BusinessConnectionId>) : IdChatIdentifier {
    override val chatId: RawChatId
        get() = chatIdWithBusinessConnectionId.first
    override val businessConnectionId: BusinessConnectionId
        get() = chatIdWithBusinessConnectionId.second

    constructor(chatId: RawChatId, businessConnectionId: BusinessConnectionId): this(chatId to businessConnectionId)
}

val ChatIdentifier.threadId: MessageThreadId?
    get() = (this as? IdChatIdentifier) ?.threadId

val ChatIdentifier.businessConnectionId: BusinessConnectionId?
    get() = (this as? IdChatIdentifier) ?.businessConnectionId

fun IdChatIdentifier.toChatId() = when (this) {
    is ChatId -> this
    is ChatIdWithThreadId -> ChatId(chatId)
    is BusinessChatId -> ChatId(chatId)
}

fun IdChatIdentifier.toChatWithThreadId(threadId: MessageThreadId) = IdChatIdentifier(chatId, threadId)
fun IdChatIdentifier.toBusinessChatId(businessConnectionId: BusinessConnectionId) = IdChatIdentifier(chatId, businessConnectionId)

/**
 * https://core.telegram.org/bots/api#formatting-options
 */
@Warning("This API have restrictions in Telegram System")
val RawChatId.userLink: String
    get() = "$internalUserLinkBeginning$this"
/**
 * https://core.telegram.org/bots/api#formatting-options
 */
@Warning("This API have restrictions in Telegram System")
val UserId.userLink: String
    get() = chatId.userLink
val User.userLink: String
    get() = id.userLink

typealias UserId = ChatId

fun RawChatId.toChatId(): ChatId = ChatId(this)
fun Long.toChatId(): ChatId = ChatId(RawChatId(this))
fun Int.toChatId(): IdChatIdentifier = RawChatId(toLong()).toChatId()
fun Byte.toChatId(): IdChatIdentifier = RawChatId(toLong()).toChatId()

/**
 * A value class representing a username that always starts with the "@" symbol.
 *
 * This class is used to encapsulate the concept of a username, enforce its format,
 * and ensure consistency when dealing with usernames throughout the application.
 *
 * @property full The full username string, guaranteed to start with "@".
 * @throws IllegalArgumentException if the provided [full] value doesn't start with "@" during initialization.
 */
@Serializable(ChatIdentifierSerializer::class)
@JvmInline
value class Username (
    val full: String
) : ChatIdentifier {
    /**
     * Retrieves the full username as a string.
     *
     * This property provides the complete username, which is guaranteed to start with the "@" symbol.
     * It represents the raw value of the username, ensuring consistency and adherence to the required format.
     */
    val username: String
        get() = full
    /**
     * A property that returns the username string without the leading "@" symbol.
     *
     * This property removes any consecutive "@" symbols at the beginning of the `full` property
     * and provides the rest of the username as a plain string.
     */
    val withoutAt
        get() = full.dropWhile { it == '@' }

    init {
        if (!full.startsWith("@")) {
            throw IllegalArgumentException("Username must starts with `@`")
        }
    }

    override fun toString(): String {
        return full
    }

    companion object {
        object WithoutAtSerializer : KSerializer<Username> {
            override val descriptor: SerialDescriptor = String.serializer().descriptor
            override fun deserialize(decoder: Decoder): Username = Username.prepare(decoder.decodeString())
            override fun serialize(encoder: Encoder, value: Username) = encoder.encodeString(value.withoutAt)
        }
        /**
         * Prepares a valid instance of [Username] by ensuring the given string starts with "@".
         *
         * @param full The input string representing the username. If the string does not start with "@",
         * it will be prefixed with "@".
         * @return A [Username] instance constructed using the provided or modified input string.
         */
        fun prepare(full: String): Username = if (full.startsWith("@")) {
            Username(full)
        } else {
            Username("@$full")
        }
    }
}

fun String.toUsername(): Username = Username.prepare(this)

@RiskFeature
object ChatIdentifierSerializer : KSerializer<ChatIdentifier> {
    private val internalSerializer = JsonPrimitive.serializer()
    override val descriptor: SerialDescriptor = internalSerializer.descriptor
    override fun deserialize(decoder: Decoder): ChatIdentifier {
        val id = internalSerializer.deserialize(decoder)

        return id.longOrNull ?.let {
            ChatId(RawChatId(it))
        } ?: id.content.let {
            Username.prepare(it)
        }
    }

    override fun serialize(encoder: Encoder, value: ChatIdentifier) {
        when (value) {
            is IdChatIdentifier -> encoder.encodeLong(value.chatId.long)
            is Username -> encoder.encodeString(value.full)
        }
    }
}

@RiskFeature
object FullChatIdentifierSerializer : KSerializer<ChatIdentifier> {
    private val internalSerializer = JsonPrimitive.serializer()
    override val descriptor: SerialDescriptor = internalSerializer.descriptor
    override fun deserialize(decoder: Decoder): ChatIdentifier {
        val id = internalSerializer.deserialize(decoder)

        return id.longOrNull ?.let {
            ChatId(RawChatId(it))
        } ?:let {
            val splitted = id.content.split("/")
            when (splitted.size) {
                2 -> {
                    val (chatId, threadId) = splitted
                    ChatIdWithThreadId(
                        chatId.toLongOrNull() ?.let(::RawChatId) ?: return@let null,
                        threadId.toLongOrNull() ?.let(::MessageThreadId) ?: return@let null
                    )
                }
                3 -> {
                    val (chatId, _, businessConnectionId) = splitted
                    BusinessChatId(
                        chatId.toLongOrNull() ?.let(::RawChatId) ?: return@let null,
                        businessConnectionId.let(::BusinessConnectionId) ?: return@let null
                    )
                }
                else -> null
            }
        } ?: id.content.let {
            Username.prepare(it)
        }
    }

    override fun serialize(encoder: Encoder, value: ChatIdentifier) {
        when (value) {
            is ChatId -> encoder.encodeLong(value.chatId.long)
            is ChatIdWithThreadId -> encoder.encodeString("${value.chatId}/${value.threadId}")
            is BusinessChatId -> encoder.encodeString("${value.chatId}//${value.businessConnectionId}")
            is Username -> encoder.encodeString(value.full)
        }
    }
}
