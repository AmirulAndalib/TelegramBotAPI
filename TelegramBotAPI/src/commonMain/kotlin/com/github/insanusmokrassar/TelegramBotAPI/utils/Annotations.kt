package com.github.insanusmokrassar.TelegramBotAPI.utils

@RequiresOptIn(
    "It is possible, that behaviour of this thing will be changed later",
    RequiresOptIn.Level.WARNING
)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.TYPE_PARAMETER
)
annotation class PreviewFeature
