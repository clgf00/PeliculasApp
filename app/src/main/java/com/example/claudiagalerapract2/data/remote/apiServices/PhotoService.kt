package com.example.claudiagalerapract2.data.remote.apiServices

import com.example.claudiagalerapract2.data.remote.di.modelo.PhotoRemote
import com.example.claudiagalerapract2.domain.modelo.Photo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PhotoService {
    @GET("/photos")
    suspend fun get(): Response<List<PhotoRemote>>


    @GET("/photos/{id}")
    suspend fun get(@Path("id") key: Int): Response<PhotoRemote>

    @GET("/albums/{albumId}/photos")
    suspend fun getPhotosForAlbum(@Path("albumId") albumId: Int): Response<List<PhotoRemote>>

}