package com.example.myapplication13re

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.example.myapplication13re.databinding.ActivityTwoBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
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

            // 스레드 안에서 만든 sum을 다른 스레드로 전달하기 위해 채널 생성
            val channel = Channel<Long>()
            val bgScope = CoroutineScope(Dispatchers.Default + Job())  // 백그라운드에서 작업하도록 함
            bgScope.launch {
                var sum = 0L
                var time = measureTimeMillis {
                    for(i in 1..4000000000) {
                        sum += i
                    }
                }
                Log.d("mobileApp", "걸린 시간 : $time")
                channel.send(sum)
            }
            val mainScope = GlobalScope.launch(Dispatchers.Main) {
                channel.consumeEach {
                    binding.tv3.text = "합계: $it"  // sum이 it으로 표현됨
                }

            }
        }
    }
}