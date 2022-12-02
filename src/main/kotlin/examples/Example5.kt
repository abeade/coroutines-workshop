package examples

import kotlinx.coroutines.*
import utils.log
import kotlin.Exception

/**
 * Example 5 CANCELLATION 1
 * ---------
 * - Check catching CancellationException and not rethrowing it (brakes structured concurrency)
 * - Wrap delay in a runBlocking and explain how brakes the propagation of the cancellation (brakes structured concurrency)
 */

fun main() {
    runBlocking(Dispatchers.Default) {
        val job = launch {
            try {
                log("running work")
                delay(1000)
                log("work completed")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                log("catched error: $e")
            }
            log("code after error")
        }
        delay(500)
        log("cancelling")
        job.cancel()
        log("cancelled")
    }
}
