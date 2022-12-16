package examples

import kotlinx.coroutines.*
import utils.log
import kotlin.Exception

/**
 * Example 6 CANCELLATION 2
 * ---------
 * - Cancellations is cooperative, the code has to cooperate to be cancellable. Suspending functions are cancellable
 * - Check the code is not cancelled properly
 * - Fix 1: Use yield inside the loop
 * - Fix 2: Us isActive in the loop condition
 */

fun main() {
    runBlocking(Dispatchers.Default) {
        val job = launch {
            val startTime = System.currentTimeMillis()
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1000)
        log("cancelling")
        job.cancelAndJoin()
        log("cancelled")
    }
}
