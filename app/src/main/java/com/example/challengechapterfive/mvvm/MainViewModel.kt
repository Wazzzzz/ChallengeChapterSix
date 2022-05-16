package com.example.challengechapterfive.mvvm

import android.app.appsearch.GetByDocumentIdRequest
import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapterfive.model.GetAllMovieResponse
import com.example.challengechapterfive.network.MoviesApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.challengechapterfive.model.*
import com.example.challengechapterfive.network.MoviesApiService

class MainViewModel: ViewModel() {
    private val _code = MutableLiveData<Int>()
    val code : LiveData<Int>
    get() = _code

    private val _movie = MutableLiveData<List<Result>>()
    val movie: LiveData<List<Result>>
    get() = _movie

    init {
        loadMovies()
    }

    private fun loadMovies() {
        MoviesApiClient.retrofitService.allMovie()
            .enqueue(object : Callback<GetAllMovieResponse>{
                override fun onResponse(
                    call: Call<GetAllMovieResponse>,
                    response: Response<GetAllMovieResponse>
                ) {
                    _movie.value = response.body()?.results
                    _code.value = response.code()
                }

                override fun onFailure(call: Call<GetAllMovieResponse>, t: Throwable) {

                }
            })
    }
}