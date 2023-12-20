package com.example.kumohCE20211391.s23

data class Song(
    val song_id: Int,
    val song_title: String,
    val album_id: Int,
    val release_date: String,
    val song_genre: String,
    val lyrics: String?
)
