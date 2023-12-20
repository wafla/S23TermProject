package com.example.kumohCE20211391.s23

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.kumohCE20211391.s23.ui.theme.S23TOP50Theme

class MainActivity : ComponentActivity() {
    private val songViewModel: SongViewModel by viewModels()
    private val albumViewModel: AlbumViewModel by viewModels()
    private val artistViewModel: ArtistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(songViewModel, albumViewModel, artistViewModel)
        }
    }
}

@Composable
fun MainScreen(songViewModel: SongViewModel, albumViewModel: AlbumViewModel, artistViewModel: ArtistViewModel){
    val songList by songViewModel.songList.observeAsState(emptyList())
    val albumList by albumViewModel.albumList.observeAsState(emptyList())
    val artistList by artistViewModel.artistList.observeAsState(emptyList())

    S23TOP50Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SongApp(songList, albumList, artistList)
        }
    }
}