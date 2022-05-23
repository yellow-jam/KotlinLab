package com.example.mydjango

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MyApplication: Application() {
    companion object{
        var apiService: ApiService
        val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://192.168.35.183:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        init {
            apiService = retrofit.create(ApiService::class.java)
        }
    }
}