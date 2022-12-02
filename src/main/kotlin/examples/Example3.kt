package examples

import kotlinx.coroutines.*
import utils.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * Example 3 DISPATCHERS 1
 * ---------
 * - Check the Thread.sleep blocks the thread
 * - Check the yield() function, remove it to see difference
 * - Recover yield
 * - Check difference when using runBlocking(Dispatchers.Default)
 * - Use runBlocking and use delay instead of Thread.sleep
 * - Use runBlocking(Dispatchers.Default) and check same behaviour with different threads (suspend instead of block)
 */

@OptIn(ExperimentalTime::class)
fun main() {
    val time: Duration = measureTime {
        runBlocking {
            log("runBlocking start")
            launch {
                log("launch1 start")
                Thread.sleep(2000)
                log("launch1 end")
            }
            launch {
                log("launch2 start")
                Thread.sleep(2000)
                log("launch2 end")
            }
            yield()
            log("runBlocking end")
        }
    }
    println("Elapsed $time")
}
