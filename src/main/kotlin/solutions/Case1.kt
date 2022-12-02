package solutions

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import utils.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

suspend fun doSomething(argument: Int) {
    delay(1000)
    log("Stuff $argument completed")
}

@OptIn(ExperimentalTime::class)
fun main() {
    val time: Duration = measureTime {
        runBlocking {
            val job1 = launch { doSomething(1) }
            val job2 = launch { doSomething(2) }
            joinAll(job1, job2)
            log("Work completed")
        }
    }
    log("Time elapsed: $time")
}
