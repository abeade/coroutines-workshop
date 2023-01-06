package testing

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.kotlin.willReturn
import utils.TestCoroutineDispatchersProvider

@OptIn(ExperimentalCoroutinesApi::class)
class FetchUserUseCaseTest {

    @Test
    fun `should return expected user`() = runBlocking {
        val repo: UserDataRepository = FakeUserDataRepository(1000)
        val useCase = FetchUserUseCase(repo)

        val result = useCase.fetchUserDataSequential()

        val expectedUser = User(
            name = "AnyName",
            assets = listOf("AnyAsset"),
            status = "NotSigned"
        )
        assertEquals(expectedUser, result)
    }

    @Test
    fun `should return expected user in parallel using not using the test dispatcher provider`() = runTest {
        //val testDispatcher = coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        val repo: UserDataRepository = FakeUserDataRepository(1000)
        val useCase = FetchUserUseCase(repo)

        val result = useCase.fetchUserDataParallel()

        val expectedUser = User(
            name = "AnyName",
            assets = listOf("AnyAsset"),
            status = "NotSigned"
        )
        assertEquals(expectedUser, result)
        // Assertion will fail because the dispatcher is actually Dispatchers.Default instead of a TestDispatcher
        assertEquals(1000, currentTime)
    }

    @Test
    fun `should return expected user in parallel using the test dispatcher provider`() = runTest {
        val repo: UserDataRepository = FakeUserDataRepository(1000)
        val useCase = FetchUserUseCase(repo, TestCoroutineDispatchersProvider)

        val result = useCase.fetchUserDataParallel()

        val expectedUser = User(
            name = "AnyName",
            assets = listOf("AnyAsset"),
            status = "NotSigned"
        )
        assertEquals(expectedUser, result)
        assertEquals(1000, currentTime)
    }

    @Test
    fun `should return expected user mock`() = runBlocking {
        val repo: UserDataRepository = mock()
        given(repo.getName()).willReturn { "AnyName" }
        given(repo.getAssets()).willReturn { listOf("AnyAsset") }
        given(repo.getStatus()).willReturn { "NotSigned" }
        val useCase = FetchUserUseCase(repo)

        val result = useCase.fetchUserDataSequential()

        val expectedUser = User(
            name = "AnyName",
            assets = listOf("AnyAsset"),
            status = "NotSigned"
        )
        assertEquals(expectedUser, result)
    }

    @Test
    fun `should return expected user parallel mock with mockk`() = runTest {
        val repo: UserDataRepository = mockk()
        coEvery { repo.getName() } coAnswers {
            delay(2000)
            "AnyName"
        }
        coEvery { repo.getAssets() } coAnswers {
            delay(2000)
            listOf("AnyAsset")
        }
        coEvery { repo.getStatus() } coAnswers {
            delay(2000)
            "NotSigned"
        }
        val useCase = FetchUserUseCase(repo, TestCoroutineDispatchersProvider)

        val result = useCase.fetchUserDataParallel()

        val expectedUser = User(
            name = "AnyName",
            assets = listOf("AnyAsset"),
            status = "NotSigned"
        )
        assertEquals(expectedUser, result)
        assertEquals(2000, currentTime)
    }

    @Test
    fun `should return expected user parallel mock with mockito`() = runTest {
        val repo: UserDataRepository = mock()
        whenever(repo.getName()).doSuspendableAnswer {
            delay(2000)
            "AnyName"
        }
        whenever(repo.getAssets()).doSuspendableAnswer {
            delay(2000)
            listOf("AnyAsset")
        }
        whenever( repo.getStatus()).doSuspendableAnswer {
            delay(2000)
            "NotSigned"
        }
        val useCase = FetchUserUseCase(repo, TestCoroutineDispatchersProvider)

        val result = useCase.fetchUserDataParallel()

        val expectedUser = User(
            name = "AnyName",
            assets = listOf("AnyAsset"),
            status = "NotSigned"
        )
        assertEquals(expectedUser, result)
        assertEquals(2000, currentTime)
    }

    @Test
    fun `pollForSignedProfile should retry up to 5 times every second while not signed`() = runTest {
        val repo: UserDataRepository = mock()
        given(repo.getStatus()).willReturn { "NotSigned" }
        val useCase = FetchUserUseCase(repo)

        var result = useCase.pollForSignedProfile()

        assertEquals(false, result)
        verify(repo, times(5)).getStatus()
        assertEquals(5000, currentTime)
    }

    @Test
    fun `pollForSignedProfile should retry until signed`() = runTest {
        val repo: UserDataRepository = mock()
        given(repo.getStatus())
            .willReturn { "NotSigned" }
            .willReturn { "Signed" }
        val useCase = FetchUserUseCase(repo)

        var result = false
        launch { result = useCase.pollForSignedProfile() }
        advanceTimeBy(2000L)

        verify(repo, times(2)).getStatus()
        assertEquals(true, result)
    }
}
