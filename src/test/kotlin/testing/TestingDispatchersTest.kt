package testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TestingDispatchersTest {

    @Test
    fun `coroutines execution are not eager with StandardTestDispatcher`() = runTest {
        var called = false

        launch {
            called = true
        }
        // StandardTestDispatcher (used by runTest as default) is not eager
        assertFalse(called)

        // Explicitly execute pending coroutines
        runCurrent()

        assertTrue(called)
    }


    @Test
    fun `coroutines execution are eager with UnconfinedTestDispatcher`() = runTest(UnconfinedTestDispatcher()) {
        var called = false

        launch {
            called = true
        }

        assertTrue(called)
    }
}
