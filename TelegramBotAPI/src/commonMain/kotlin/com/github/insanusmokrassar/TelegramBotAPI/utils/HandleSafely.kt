package com.github.insanusmokrassar.TelegramBotAPI.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.supervisorScope

/**
 * It will run [block] inside of [supervisorScope] to avoid problems with catching of exceptions
 *
 * @param [onException] Will be called when happen exception inside of [block]. By default will throw exception - this
 * exception will be available for catching
 */
suspend inline fun <T> handleSafely(
    noinline onException: suspend (Exception) -> T = { throw it },
    noinline block: suspend CoroutineScope.() -> T
): T {
    return try {
        supervisorScope(block)
    } catch (e: Exception) {
        onException(e)
    }
}
