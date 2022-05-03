package com.example.myapplication13re

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication13re.databinding.ActivityMainBinding
import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity() {
    var datas:MutableList<String>? = null
    lateinit var adapter : MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 13-3 전체 화면 설정
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {  // 버전 >= 30
            window.setDecorFitsSystemWindows(false)  // 전체화면으로 설정
            val controller = window.insetsController
            if(controller != null){
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()) // 상태바 or 하단 내비게이션바를 가림
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE  // 스와이프 동작 -> 숨겨져 있던 부분이 나옴
            }
        }
        else {  // 버전 <= 29
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }


        // 사후 처리 메소드로 [받기]
        val requestLauncher:ActivityResultLauncher<Intent>
            = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data!!.getStringExtra("result")?.let{
                datas?.add(it)
                adapter.notifyDataSetChanged()
            }
            //Log.d("MobileApp", d3!! )
        }


        binding.fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("data1", "mobile")
            intent.putExtra("data2", "app")
            //startActivityForResult(intent, 10) // [보내기1] //startActivity(intent) // 단순 보내기
            requestLauncher.launch(intent) // 사후 처리 메소드로 [보내기]
        }

        datas = mutableListOf<String>()
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from todo_tb", null)
        while (cursor.moveToNext()){
            datas?.add(cursor.getString(1))  // 테이블 속성: 0번째 id, 1번째 todo(string)
        }
        db.close()

        /* 번들 대신 데이터베이스 사용
        datas = savedInstanceState?.let {
            it.getStringArrayList("mydatas")?.toMutableList()
        } ?:let{
            mutableListOf<String>()
        }
         */

        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(datas)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }
    /* 번들 대신 데이터베이스 사용
    // [Bundle 받기]
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putStringArrayList("mydatas", ArrayList(datas))
    }
     */

    // [받기1]
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==10 && resultCode== RESULT_OK) {
            val d3 = data?.getStringExtra("test")
            Log.d("MobileApp", d3!! )
        }
    }
    */
}