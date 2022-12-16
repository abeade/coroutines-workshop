package cases

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import utils.ExternalCompletion
import utils.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    val time: Duration = measureTime {
        runBlocking {
            launch {
                val answer = ExternalCompletion().compute()
                log("Answer is: $answer")
            }
            launch {
                log("Other coroutine started")
            }
        }
    }
    log("Time elapsed: $time")
}
