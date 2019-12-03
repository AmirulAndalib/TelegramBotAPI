package com.github.insanusmokrassar.TelegramBotAPI.types.polls

import com.github.insanusmokrassar.TelegramBotAPI.types.textField
import com.github.insanusmokrassar.TelegramBotAPI.types.votesCountField
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable(PollOptionSerializer::class)
sealed class PollOption {
    abstract val text: String
    abstract val votes: Int
}

@Serializable
data class AnonymousPollOption (
    @SerialName(textField)
    override val text: String,
    @SerialName(votesCountField)
    override val votes: Int
) : PollOption()

object PollOptionSerializer : KSerializer<PollOption> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName(PollOption::class.simpleName ?: "PollOption")

    override fun deserialize(decoder: Decoder): PollOption = AnonymousPollOption.serializer().deserialize(
        decoder
    )

    override fun serialize(encoder: Encoder, obj: PollOption) {
        when (obj) {
            is AnonymousPollOption -> AnonymousPollOption.serializer().serialize(
                encoder,
                obj
            )
        }
    }
}
