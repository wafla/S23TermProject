package com.example.kumohCE20211391.s23

import retrofit2.http.GET

interface ArtistApi {
    @GET("artist")
    suspend fun getArtists(): List<Artist>
}