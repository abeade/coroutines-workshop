package cases

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
                val answer = ExternalCallback().compute()
                log("Answer is: $answer")
            }
            launch {
                log("launch completed")
            }
        }
    }
    log("Time elapsed: $time")
}
