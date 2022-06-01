package com.example.mydjango

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("test")
    fun getList(
        @Query("format") returnType: String
    ): Call<MutableList<hInfoModel>>

    @POST("test/")  // test/ 뒤에 슬래시 필수
    fun postData(
        @Body data:hInfoModel
    ): Call<hInfoModel>
}