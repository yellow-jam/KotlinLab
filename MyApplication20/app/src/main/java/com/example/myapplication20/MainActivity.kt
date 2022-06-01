package com.example.myapplication20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication20.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* 카카오 로그인 해시 키 구하기
        val keyHash = Utility.getKeyHash(this)
        Log.d("mobileApp", keyHash)
        */

        myCheckPermission(this)
        /* 이미지 버튼 */
        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                startActivity(Intent(this, AddActivity::class.java))
            }
            else{
                Toast.makeText(this, "인증을 진행해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            // 로그인: 별도의 액티비티를 만들어 처리
            val intent = Intent(this, AuthActivity::class.java)
            if(binding.btnLogin.text.equals("로그인")) // 로그아웃 상태
                intent.putExtra("data", "logout")
            else if(binding.btnLogin.text.equals("로그아웃")) // 로그인 상태
                intent.putExtra("data", "login")
            startActivity(intent)
        }
    }

    // AuthActivity에서 돌아온 후
    override fun onStart() {
        super.onStart()
        if(MyApplication.checkAuth() || MyApplication.email != null){  // 검증된 이메일인가
            // 로그인 상태
            binding.btnLogin.text = "로그아웃"
            binding.authTv.text = "${MyApplication.email}님 반갑습니다."
            binding.authTv.textSize = 16F

            binding.mainRecyclerView.visibility = View.VISIBLE // 이미지 리사이클러뷰
            makeRecyclerView()
        }
        else{
            // 로그아웃 상태
            binding.btnLogin.text = "로그인"
            binding.authTv.text = "덕성 모바일"
            binding.authTv.textSize = 24F

            binding.mainRecyclerView.visibility = View.GONE // 이미지 리사이클러뷰
        }
    }

    private fun makeRecyclerView() {
        MyApplication.db.collection("news")  // AddActivity에서 news에 저장하기로 함
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for(document in result){
                    val item = document.toObject(ItemData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = MyAdapter(this, itemList)
            }
            .addOnFailureListener {
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }

    }
}