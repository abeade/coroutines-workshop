package utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object DefaultCoroutineDispatchersProvider: CoroutineDispatchersProvider {
    override val default: CoroutineContext = Dispatchers.Default
    override val io: CoroutineContext = Dispatchers.IO
    override val unconfined: CoroutineContext= Dispatchers.Unconfined
}
