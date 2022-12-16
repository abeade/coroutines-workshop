package solutions

import kotlinx.coroutines.future.await
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
                val answer = ExternalCompletion().computeAsync().await()
                log("Answer is: $answer")
            }
        }
    }
    log("Time elapsed: $time")
}
