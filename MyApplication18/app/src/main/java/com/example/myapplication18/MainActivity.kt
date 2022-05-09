package com.example.myapplication18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication18.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
    }
}