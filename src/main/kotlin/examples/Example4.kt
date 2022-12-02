package examples

import kotlinx.coroutines.*
import utils.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * Example 4 DISPATCHERS 2
 * ---------
 * - Check disptachers IO has 64 threads and Defautl 10 threats per M1 CPU
 * - Check time when using more threads in the loop
 * - Use delay and check how it don't affect again to 11 threads
 */

@OptIn(ExperimentalTime::class)
fun main() {
    val time: Duration = measureTime {
        runBlocking(Dispatchers.Default) {
            repeat(10) { index ->
                launch {
                    Thread.sleep(1000)
                    log("task $index completed")
                }
            }
        }
        log("work completed")
    }
    log("Time elapsed: $time")
}
