package utils

import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchersProvider {
    val default: CoroutineContext
    val io: CoroutineContext
    val unconfined: CoroutineContext
}
