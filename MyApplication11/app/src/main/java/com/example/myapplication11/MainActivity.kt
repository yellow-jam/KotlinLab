package com.example.myapplication11

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView // 임포트 주의 androidx
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication11.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    class MyFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments = listOf(Fragment1(), Fragment2(), Fragment3())
        }
        override fun getItemCount(): Int {
            return fragments.size
        }
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var toggle : ActionBarDrawerToggle // 11-6 액션바드로어 토글

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)  // xml에 등록한 툴바 적용, 액션 바 없애기는 theme 파일에서

        // 11-6 액션바드로어 토글
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_open, R.string.drawer_close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // 11 프래그먼트 매니저
        /*
        val fragmentManager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction() // 트랜잭션으로 프래그먼트 관리
        var fragment = Fragment1()
        transaction.add(R.id.fragment_content, fragment) // 트랜잭션에.추가(이 영역에, 이 프래그먼트를)
        transaction.commit() // 동작 실행
        */

        // 11-5 뷰 페이저2
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL  // 가로 방향으로 프래그먼트 전환
        binding.viewpager.adapter = MyFragmentAdapter(this)

        // 12-2 탭 레이아웃을 뷰 페이저와 연동
        TabLayoutMediator(binding.tab1, binding.viewpager){
            tab, position -> tab.text = "TAB ${position+1}"
        }.attach()

        // 12-3 내비게이션 드로어에 이벤트 리스너 추가
        binding.mainDrawerView.setNavigationItemSelectedListener {
            Log.d("mobileApp", "Navigation selected... ${it.title}")
            true
        }

        // 12-4 플로팅 버튼
        binding.fab.setOnClickListener{
            when(binding.fab.isExtended) {
                true -> binding.fab.shrink()
                false -> binding.fab.extend()
            }
        }
    }

    /* 액션 바 메뉴 */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 메뉴 만들기 방법 (1) 코틀린 코드에 작성
//        val menuItem1 : MenuItem? = menu?.add(0,0,0, "메뉴1") // 두 번째 Int가 itemId(메뉴 항목 식별자)
//        val menuItem2 : MenuItem? = menu?.add(0,1,0, "메뉴2") // 두 번째 Int가 itemId
        // 메뉴 만들기 방법 (2) res/menu/menu_main.xml
        menuInflater.inflate(R.menu.menu_main, menu) // 두 번째 - 어디에 적용할 것인지: 옵션메뉴에 적용하겠다 menu

        // 액션 뷰 이용: SearchView - menu_main.xml
        val menuSearch = menu?.findItem(R.id.menu_search)
        val searchView = menuSearch?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
//                binding.tv1.text = p0
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) return true // 11-6 토글 - 햄버거 버튼이 눌렸다면 true
        when (item.itemId) {
            // 코틀린코드에서추가한메뉴: 2번째 매개변수 - 메뉴 항목 식별자 // menu 리소스 xml로 추가한메뉴: 태그의 id로
            R.id.menu1 -> {
//                binding.tv1.setTextColor(Color.BLUE)
//                binding.tv1.setText("텍스트 변경?")
                true
            }
            R.id.menu2 -> {

                true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}