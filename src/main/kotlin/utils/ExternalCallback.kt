package utils

import kotlin.concurrent.thread

class ExternalCallback {

    private var thread: Thread? = null

    fun compute(): Int {
        Thread.sleep(2000)
        return 42
    }

    fun computeAsync(callback: (Int) -> Unit) {
        thread = thread(start = true) { callback(compute()) }
    }

    fun cancel() {
        thread?.interrupt()
    }
}
