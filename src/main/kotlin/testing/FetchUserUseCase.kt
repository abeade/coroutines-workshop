package testing

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import utils.CoroutineDispatchersProvider
import utils.DefaultCoroutineDispatchersProvider
import kotlin.coroutines.CoroutineContext

class FetchUserUseCase(
    private val repo: UserDataRepository,
    private val dispatchers: CoroutineDispatchersProvider = DefaultCoroutineDispatchersProvider
) {

    suspend fun fetchUserDataSequential(): User = coroutineScope {
        val name = repo.getName()
        val assets = repo.getAssets()
        val status = repo.getStatus()
        User(
            name = name,
            assets = assets,
            status = status
        )
    }

    suspend fun fetchUserDataParallel(): User = withContext(dispatchers.default) {
        val name = async { repo.getName() }
        val assets = async { repo.getAssets() }
        val status = async { repo.getStatus() }
        User(
            name = name.await(),
            assets = assets.await(),
            status = status.await()
        )
    }

    suspend fun pollForSignedProfile(): Boolean {
        var retry = 0
        while (retry++ < 5 && repo.getStatus() != "Signed") {
            delay(1000)
        }
        return retry < 5
    }
}
