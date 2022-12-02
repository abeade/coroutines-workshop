package utils

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

class ExternalCompletion {

    fun compute(): Int {
        Thread.sleep(2000)
        return 42
    }

    fun computeAsync(): CompletionStage<Int> {
        return CompletableFuture.supplyAsync { compute() }
    }
}
