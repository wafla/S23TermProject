package com.example.kumohCE20211391.s23

import retrofit2.http.GET

interface AlbumApi {
    @GET("album")
    suspend fun getAlbums(): List<Album>
}