package com.example.androidlab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.Ch7ConstraintBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        //바인딩 객체 획득
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.visibleBtn.setOnClickListener {
            binding.targetView.visibility = View.VISIBLE
        }

        binding.invisibleBtn.setOnClickListener {
            binding.targetView.visibility = View.INVISIBLE
        }

         */
        val binding3 = Ch7ConstraintBinding.inflate(layoutInflater)
        setContentView(binding3.root)

        /*
        val binding2 = Ch7LayoutBinding.inflate(layoutInflater)
        setContentView(binding2.root)
        */
    }
}