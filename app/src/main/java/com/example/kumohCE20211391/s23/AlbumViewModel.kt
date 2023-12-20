package com.example.kumohCE20211391.s23

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumViewModel : ViewModel() {
    private val SERVER_URL = "https://port-0-s23w10backend-dr3h12alpy159mo.sel4.cloudtype.app/"
    private val albumApi: AlbumApi
    private val _albumList = MutableLiveData<List<Album>>()
    val albumList: LiveData<List<Album>>
        get() = _albumList

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        albumApi = retrofit.create(AlbumApi::class.java)
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch{
            try{
                val response = albumApi.getAlbums()
                _albumList.value = response
            } catch(e: Exception){
                Log.e("fetchData()", e.toString())
            }
        }
    }
}