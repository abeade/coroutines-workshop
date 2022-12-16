package testing

interface UserDataRepository {
    suspend fun getName(): String
    suspend fun getAssets(): List<String>
    suspend fun getStatus(): String
}
