package com.github.insanusmokrassar.TelegramBotAPI.types

import com.github.insanusmokrassar.TelegramBotAPI.CommonAbstracts.CommonContactData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    @SerialName(phoneNumberField)
    override val phoneNumber: String,
    @SerialName(firstNameField)
    override val firstName: String,
    @SerialName(lastNameField)
    override val lastName: String? = null,
    @SerialName(userIdField)
    val userId: UserId? = null,
    @SerialName(vcardField)
    override val vcard: String? = null
) : CommonContactData
