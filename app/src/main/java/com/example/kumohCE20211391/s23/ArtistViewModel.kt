package com.example.kumohCE20211391.s23

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistViewModel : ViewModel() {
    private val SERVER_URL = "https://port-0-s23w10backend-dr3h12alpy159mo.sel4.cloudtype.app/"
    private val artistApi: ArtistApi
    private val _artistList = MutableLiveData<List<Artist>>()
    val artistList: LiveData<List<Artist>>
        get() = _artistList

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        artistApi = retrofit.create(ArtistApi::class.java)
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch{
            try{
                val response = artistApi.getArtists()
                _artistList.value = response
            } catch(e: Exception){
                Log.e("fetchData()", e.toString())
            }
        }
    }
}