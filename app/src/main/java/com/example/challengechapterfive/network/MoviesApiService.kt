package com.example.challengechapterfive.network

import com.example.challengechapterfive.model.GetAllMovieResponse
import com.example.challengechapterfive.model.Result
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MoviesApiService{
    @GET("3/movie/popular?api_key=bb0de6e5ce262c74f7f5db0e14ae5a72")
    fun allMovie(): Call<GetAllMovieResponse>
}

//private const val BASE_URL = "https://www.themoviedb.org/"
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(GsonConverterFactory.create())
//    .baseUrl(BASE_URL)
//    .build()
//
//interface MoviesApiService {
//    @GET("3/movie/popular?api_key=bb0de6e5ce262c74f7f5db0e14ae5a72")
//    fun allMovie(): Call<GetAllMovieResponse>
//}
//
//object MoviesApi{
//    val retrofitService: MoviesApiService by lazy {
//        retrofit.create(MoviesApiService::class.java)
//    }
//    private val logging: HttpLoggingInterceptor
//    get() {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        return httpLoggingInterceptor.apply {
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        }
//    }
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(logging)
//        .build()
//
//    val instance: MoviesApiService by lazy {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        retrofit.create(MoviesApiService::class.java)
//    }
//}