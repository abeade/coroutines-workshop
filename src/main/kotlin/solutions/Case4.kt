package solutions

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import utils.ExternalCallback
import utils.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    val time: Duration = measureTime {
        runBlocking {
            launch {
                val answer = suspendCancellableCoroutine { continuation ->
                    val external = ExternalCallback()
                    external.computeAsync { result -> continuation.resumeWith(Result.success(result)) }
                    continuation.invokeOnCancellation { external.cancel() }
                }
                log("Answer is: $answer")
            }
        }
    }
    log("Time elapsed: $time")
}
