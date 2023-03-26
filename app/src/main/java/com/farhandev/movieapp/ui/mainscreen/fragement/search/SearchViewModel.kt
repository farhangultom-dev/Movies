package com.farhandev.movieapp.ui.mainscreen.fragement.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhandev.movieapp.service.local.MovieDb
import com.farhandev.movieapp.service.local.MovieModel
import com.farhandev.movieapp.service.network.ApiConfig
import com.farhandev.movieapp.service.network.response.DetailMovieResponse
import com.farhandev.movieapp.service.network.response.GenreResponse
import com.farhandev.movieapp.service.network.response.GenresItem
import com.farhandev.movieapp.service.network.response.SarchMovieResponse
import com.farhandev.movieapp.util.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _listMovie = MutableLiveData<List<DetailMovieResponse>>()
    val listMovie: LiveData<List<DetailMovieResponse>> = _listMovie

    private val _listGenre = MutableLiveData<List<GenresItem>>()
    val listGenre: LiveData<List<GenresItem>> = _listGenre

    fun getSearchMovie(movie: String, apikey: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchMovie(movie,apikey)
        client.enqueue(object : Callback<SarchMovieResponse> {
            override fun onResponse(
                call: Call<SarchMovieResponse>,
                response: Response<SarchMovieResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listMovie.value = response.body()?.results
                }else{
                    Log.e("SearchViewModel", "OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SarchMovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("SearchViewModel", "OnFailure: ${t.message.toString()}")
            }
        })
    }

    fun getGenre() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGenreMovie()
        client.enqueue(object : Callback<GenreResponse> {
            override fun onResponse(
                call: Call<GenreResponse>,
                response: Response<GenreResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listGenre.value = response.body()?.genres
                }else{
                    Log.e("SearchViewModel", "OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("SearchViewModel", "OnFailure: ${t.message.toString()}")
            }
        })
    }

    fun getMovieByGenre(genreId: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMoviesByGenre(NetworkConfig.API_KEY, genreId)
        client.enqueue(object : Callback<SarchMovieResponse> {
            override fun onResponse(
                call: Call<SarchMovieResponse>,
                response: Response<SarchMovieResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listMovie.value = response.body()?.results
                }else{
                    Log.e("SearchViewModel", "OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SarchMovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("SearchViewModel", "OnFailure: ${t.message.toString()}")
            }
        })
    }
}