package testing

import kotlinx.coroutines.delay

class FakeUserDataRepository(private val delay: Long = 0): UserDataRepository {

    override suspend fun getName(): String {
        delay(delay)
        return "AnyName"
    }

    override suspend fun getAssets(): List<String> {
        delay(delay)
        return listOf("AnyAsset")
    }

    override suspend fun getStatus(): String {
        delay(delay)
        return "NotSigned"
    }
}
