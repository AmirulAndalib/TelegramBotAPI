package com.github.insanusmokrassar.TelegramBotAPI.utils.extensions

fun String.toMarkdown(): String {
    return replace(
        "*",
        "\\*"
    ).replace(
        "_",
        "\\_"
    )
}

fun String.toHtml(): String = replace(
    "<",
    "&lt;"
).replace(
    ">",
    "&gt;"
).replace(
    "&",
    "&amp;"
).replace(
    "\"",
    "&quot;"
)
