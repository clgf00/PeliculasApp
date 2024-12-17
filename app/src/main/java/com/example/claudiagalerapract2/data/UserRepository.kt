package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.UserService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toUserDetail
import com.example.claudiagalerapract2.domain.modelo.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UserRepository@Inject constructor(
    private val userService: UserService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,

    )  {

    suspend fun fetchUsers(): NetworkResult<List<User>?> {
        return galleryRemoteDataSource.fetchUsers()

    }

    suspend fun fetchUser(id: Int): NetworkResult<User> {

        try {
            val response = userService.get(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toUserDetail())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }

    }

    fun getUserById(userId: Int, callback: (User?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = userService.get(userId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    callback(response.body()?.toUserDetail())
                } else {
                    callback(null)
                }
            }
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}