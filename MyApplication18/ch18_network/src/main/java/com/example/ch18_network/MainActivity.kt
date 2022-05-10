package com.example.ch18_network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch18_network.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RetrofitFragment()
        val bundle = Bundle()  // 프래그먼트에 값 전달 - 번들 객체 이용
        binding.searchBtn.setOnClickListener {
            when(binding.rGroup.checkedRadioButtonId){
                R.id.json -> bundle.putString("returnType", "json")
                R.id.xml -> bundle.putString("returnType", "xml")
                else -> bundle.putString("returnType", "json")
            }
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, fragment)  // (어디에, 어떤 프래그먼트를)
                .commit()
        }
    }
}