package com.example.myapplication13re

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication13re.databinding.ActivityTwoBinding
import kotlin.system.measureTimeMillis

class TwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_two)
        val binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button6.setOnClickListener {
            /*
            var sum = 0L
            var time = measureTimeMillis {
                for(i in 1..4000000000) {
                    sum += i
                }
            }
            Log.d("mobileApp", "걸린 시간 : $time")
            binding.tv3.text = "합계: $sum"
             */

        }
    }
}