package com.example.ch18_image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_image.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            var call: Call<PageListModel> = MyApplication.networkService.getList(
                1,
                10,
                "json",
                "pYVi5WRhkWtEwEK/I4kgN7b4rNT0ilJBAD0ZrcvBAAFFgV3kqfOSQ9cQn5eEczFo+9O1Q1g5b0UiGp10XfJcOA=="
            )
            call?.enqueue(object:Callback<PageListModel>{
                override fun onResponse(
                    call: Call<PageListModel>,
                    response: Response<PageListModel>
                ) {
                    if(response.isSuccessful){
                        Log.d("mobileApp", "${response.body()}")
                        binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)  // Call 객체 안에 들어가있기 때문에 @MainActivity 명시
                        binding.retrofitRecyclerView.adapter = MyAdapter(this@MainActivity, response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                    Log.d("mobileApp", "onFailure....")
                }
            })
        }
    }
}