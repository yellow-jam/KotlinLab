package com.example.myapplication13re

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication13re.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas:MutableList<String>? = null
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.adapter = MyAdapter(datas)

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("data1", "mobile")
            intent.putExtra("data2", "app")
            startActivity(intent)
        }
    }
}