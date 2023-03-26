package com.farhandev.movieapp.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhandev.movieapp.service.network.ApiConfig
import com.farhandev.movieapp.service.network.ApiService
import com.farhandev.movieapp.service.network.response.*
import com.farhandev.movieapp.util.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.channels.NetworkChannel

class DetailMovieViewModel : ViewModel() {
    private val _videoUrl = MutableLiveData<List<ResultsItemVideo>>()
    val videoUrl: LiveData<List<ResultsItemVideo>> = _videoUrl

    private val _reviews = MutableLiveData<List<ResultsItem>>()
    val reviews: LiveData<List<ResultsItem>> = _reviews

    fun getVideoUrl(movieId: String){
        val client = ApiConfig.getApiService().getVideoMovie(
            movieId, NetworkConfig.API_KEY
        )
        client.enqueue(object : Callback<VideoResponse>{
            override fun onResponse(
                call: Call<VideoResponse>,
                response: Response<VideoResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.results.isNullOrEmpty()){
                        Log.e("DetailViewModel", "Data Not Found")
                    }else{
                        _videoUrl.value = response.body()?.results
                    }
                }else{
                    Log.e("DetailViewModel", "OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.e("HomeViewModel", "OnFailure: ${t.message.toString()}")
            }
        })
    }

    fun getReviews(movieId: String){
        val client = ApiConfig.getApiService().getReviews(
            movieId, NetworkConfig.API_KEY
        )
        client.enqueue(object : Callback<ReviewResponse>{
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.results.isNullOrEmpty()){
                        Log.e("DetailViewModel", "Data Not Found")
                    }else{
                        _reviews.value = response.body()?.results
                    }
                }else{
                    Log.e("DetailViewModel", "OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Log.e("HomeViewModel", "OnFailure: ${t.message.toString()}")
            }
        })
    }
}