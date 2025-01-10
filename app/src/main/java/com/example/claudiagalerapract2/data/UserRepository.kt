package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.local.UserDao
import com.example.claudiagalerapract2.data.local.modelo.UserEntity
import com.example.claudiagalerapract2.data.local.modelo.toUser
import com.example.claudiagalerapract2.data.remote.apiServices.UserService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.di.IoDispatcher
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toUserDetail
import com.example.claudiagalerapract2.domain.modelo.User
import com.example.claudiagalerapract2.ui.common.Constantes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userService: UserService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
    private val userDao: UserDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,


    ) {
    suspend fun fetchUserConFlow(): Flow<NetworkResult<List<User>>> {
        return flow {

            emit(NetworkResult.Loading())
            val result = galleryRemoteDataSource.fetchUsers()
            emit(result)

        }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: Constantes.ERROR))
            }
            .flowOn(dispatcher)
    }

    suspend fun insert(user: User) {
        withContext(Dispatchers.IO) {
            val userEntity = UserEntity(username = user.username, password = user.password)
            userDao.insert(userEntity)

        }
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

    suspend fun getUserByUsername(username: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByUsername(username)?.toUser()
        }
    }

    suspend fun getUserByUsernameAndPassword(username: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByUsernameAndPassword(username, password)?.toUser()
        }
    }


    private fun <T> error(message: String): NetworkResult<T> =
        NetworkResult.Error(message)

}