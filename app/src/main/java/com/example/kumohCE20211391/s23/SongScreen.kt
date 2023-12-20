package com.example.kumohCE20211391.s23

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage

enum class SongScreen{
    List,
    Detail
}

@Composable
fun SongApp(songList: List<Song>, albumList: List<Album>, artistList: List<Artist>){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SongScreen.List.name,
    ){
        composable(route = SongScreen.List.name){
            SongList(songList, albumList, artistList){
                navController.navigate(it)
            }
        }
        composable(route = SongScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index"){
                type = NavType.IntType
            })
        ){
            val index = it.arguments?.getInt("index") ?: -1
            if(index >= 0)
                SongDetail(songList[index], albumList, artistList)
        }
    }
}

@Composable
fun SongList(list: List<Song>, albumList: List<Album>, artistList: List<Artist>, onNavigateToDetail: (String) -> Unit){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        items(list){ song ->
            val album = getAlbumById(song.album_id, albumList)
            val albumUrl = getAlbumUrl(song.album_id, albumList)
            val artistName = getArtistByAlbum(album?.artist_id, artistList)?.artist_name ?: "알 수 없는 가수"
            val index = list.indexOf(song)
            if (albumUrl != null) {
                SongItem(artistName, albumUrl, index, list[index], onNavigateToDetail)
            }
        }
    }
}

@Composable
fun SongItem(artistName: String, url: String, index: Int, song: Song, onNavigateToDetail: (String) -> Unit) {

    Card(
        modifier = Modifier.clickable{
           onNavigateToDetail(SongScreen.Detail.name+"/$index")
        },
        elevation = CardDefaults.cardElevation(8.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color(255, 210, 210))
                .padding(8.dp)
        ) {
            AsyncImage(
                model = url,
                contentDescription = "노래 앨범 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextTitle(song.song_title)
                TextArtist(artistName)
            }
        }
    }
}

@Composable
fun SongDetail(song: Song, albumList: List<Album>, artistList: List<Artist>){
    val album = getAlbumById(song.album_id, albumList)
    val albumUrl = getAlbumUrl(song.album_id, albumList)
    val artistName = getArtistByAlbum(album?.artist_id, artistList)?.artist_name ?: "알 수 없는 가수"
    val artistUrl = getArtistUrl(album?.artist_id, artistList)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            song.song_title,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (album != null) {
            Text(
                album.album_name,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = albumUrl,
            contentDescription = "노래 앨범 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                song.song_genre,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                song.release_date,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = artistUrl,
                contentDescription = "가수 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(artistName, fontSize = 30.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))
        song.lyrics?.let{
            Text(
                it,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }
    }
}

@Composable
fun TextTitle(title: String){
    Text(title, fontSize = 25.sp)
}

@Composable
fun TextAlbum(album: String){
    Text(album, fontSize = 20.sp)
}

@Composable
fun TextArtist(artist: String){
    Text(artist, fontSize = 20.sp)
}

fun getAlbumById(albumId: Int, albumList: List<Album>): Album? {
    return albumList.find { it.album_id == albumId }
}

fun getAlbumUrl(albumId: Int, albumList: List<Album>): String? {
    val album = albumList.find { it.album_id == albumId }
    return album?.image_url
}

fun getArtistByAlbum(artistId: Int?, artistList: List<Artist>): Artist? {
    return artistId?.let { id ->
        artistList.find { it.artist_id == id }
    }
}

fun getArtistUrl(artistId: Int?, artistList: List<Artist>): String?{
    val artist = artistList.find{it.artist_id == artistId}
    return artist?.image_url
}