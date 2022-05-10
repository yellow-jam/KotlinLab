package com.example.ch18_network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch18_network.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            when(binding.rGroup.checkedRadioButtonId){
                R.id.json -> ""
                R.id.xml -> ""
                else -> "json"
            }
        }
    }
}