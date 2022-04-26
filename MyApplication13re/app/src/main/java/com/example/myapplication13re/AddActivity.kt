package com.example.myapplication13re

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.example.myapplication13re.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var d1 = intent.getStringExtra("data1")
        var d2 = intent.getStringExtra("data2")

        binding.tv.text = (d1+d2)

        /*
        binding.button1.setOnClickListener {
            intent.putExtra("test", "world")
            setResult(RESULT_OK, intent)  // [돌려보내기1]
            finish()
        }
        */

        // 13-4 태스크 제어
        binding.button5.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        // 암시적 인텐트 단순 실행
        binding.button2.setOnClickListener {
            val intent = Intent()
            intent.action = "ACTION_EDIT"
            intent.data = Uri.parse("http://www.google.com")
            startActivity(intent)
        }

        // 13-3 소프트키보드 제어
        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.button3.setOnClickListener {
            binding.addEditView.requestFocus()  // 강제로 포커스 설정
            manager.showSoftInput(binding.addEditView, InputMethodManager.SHOW_IMPLICIT)
        }
        binding.button4.setOnClickListener {
            manager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

    }

    // 13-4 싱글 탑
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    // 메뉴 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add_save) {
            intent.putExtra("result", binding.addEditView.text.toString())
            setResult(RESULT_OK, intent) // [돌려보내기]
            finish()
            return true
        }
        return false
    }
}