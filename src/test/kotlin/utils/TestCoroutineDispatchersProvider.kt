package utils

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object TestCoroutineDispatchersProvider: CoroutineDispatchersProvider {
    override val default: CoroutineContext = EmptyCoroutineContext
    override val io: CoroutineContext = EmptyCoroutineContext
    override val unconfined: CoroutineContext= EmptyCoroutineContext
}
