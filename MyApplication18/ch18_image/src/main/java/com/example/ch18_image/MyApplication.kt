package com.example.ch18_image

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    companion object{
        var networkService: NetworkService
        val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/1262000/")
                .addConverterFactory(GsonConverterFactory.create()) // json으로 컨버트
                .build()
        init {
            networkService = retrofit.create(NetworkService::class.java)
        }
    }
}