package examples

import kotlinx.coroutines.*
import utils.log

/**
 * Example 2 SCOPE & CONTEXT
 * ---------
 * - Check coroutine name
 * - Call function inside withContext to see how the context is inherited
 * - Check parallel decomposition with coroutineScope and withContext in suspend function
 * - Check withContext changes dispatcher the dispatcher but is a suspend function (no run in parallel)
 */

suspend fun function() = withContext(Dispatchers.Default) {
    log("function withContext")
}

fun main() {
    log("main")
    runBlocking {
        log("runBlocking start")
        launch {
            log("launch start")
            withContext(Dispatchers.Default) {
                log("withContext")
            }
            coroutineScope {
                log("coroutineScope")
            }
            function()
            log("launch end")
        }
        log("runBlocking end")
    }
}
