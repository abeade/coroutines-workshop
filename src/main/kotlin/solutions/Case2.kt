package solutions

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import utils.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

suspend fun getSomething(argument: Int): Int {
    delay(1000)
    log("Foo $argument completed")
    return argument + 10
}

@OptIn(ExperimentalTime::class)
fun main() {
    val time: Duration = measureTime {
        runBlocking {
            val foo1 = async { getSomething(1) }
            val foo2 = async { getSomething(2) }
            log("Foo1: ${foo1.await()} Foo2: ${foo2.await()}")
        }
    }
    log("Time elapsed: $time")
}
