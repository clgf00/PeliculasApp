package com.example.claudiagalerapract2.data.remote.di.dataSource

import com.example.claudiagalerapract2.data.remote.apiServices.AlbumService
import com.example.claudiagalerapract2.data.remote.apiServices.PhotoService
import com.example.claudiagalerapract2.data.remote.apiServices.TodoService
import com.example.claudiagalerapract2.data.remote.apiServices.UserService
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toAlbum
import com.example.claudiagalerapract2.data.remote.di.modelo.toPhoto
import com.example.claudiagalerapract2.data.remote.di.modelo.toTodo
import com.example.claudiagalerapract2.data.remote.di.modelo.toUser
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.domain.modelo.User
import javax.inject.Inject

class GalleryRemoteDataSource @Inject constructor(
    private val userService: UserService,
    private val albumService: AlbumService,
    private val todoService: TodoService,
    private val photoService: PhotoService



) : BaseApiResponse() {
    suspend fun fetchUsers(): NetworkResult<List<User>> =
        safeApiCall { userService.get() }.map { lista ->
            lista.map { it.toUser() }
        }

    suspend fun fetchPhotos(): NetworkResult<List<Photo>> =
        safeApiCall { photoService.get() }.map { lista ->
            lista.map { it.toPhoto() }
        }

    suspend fun fetchAlbums(): NetworkResult<List<Album>> =
        safeApiCall { albumService.getAll() }.map { lista ->
            lista.map { it.toAlbum() }
        }

    suspend fun fetchTodos(): NetworkResult<List<Todo>> =
        safeApiCall { todoService.get() }.map { lista ->
            lista.map { it.toTodo() }
        }
}

