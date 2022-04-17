package com.example.myapplication13re

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication13re.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var d1 = intent.getStringExtra("data1")
        var d2 = intent.getStringExtra("data2")

        binding.tv.text = (d1+d2)

        binding.button1.setOnClickListener {
            intent.putExtra("test", "world")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}