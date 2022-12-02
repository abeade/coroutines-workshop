package cases

import kotlinx.coroutines.delay
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
            doSomething(1)
            doSomething(2)
            log("Work completed")
        }
    }
    log("Time elapsed: $time")
}
