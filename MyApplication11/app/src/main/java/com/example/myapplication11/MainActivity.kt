package com.example.myapplication11

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {
    class MyFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments = listOf(Fragment1(), Fragment2(), Fragment3())
        }
        override fun getItemCount(): Int {
            //TODO("Not yet implemented")
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            //TODO("Not yet implemented")
            return fragments[position]
        }
    }


    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var toggle : ActionBarDrawerToggle // 11-6 액션바드로우 토글
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        // 11-6 액션바드로우 토글
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_open, R.string.drawer_close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // 11 프래그먼트 매니저
        /*
        val fragmentManager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()
        var fragment = Fragment1()
        transaction.add(R.id.fragment_content, fragment)
        transaction.commit()
        */

        // 11-5 뷰 페이저2
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 가로 방향으로 프래그먼트 전환
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 가로 방향으로 프래그먼트 전환
        binding.viewpager.adapter = MyFragmentAdapter(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //val menuItem1 : MenuItem? = menu?.add(0,0,0, "메뉴1") // 두 번째 Int가 itemId(메뉴 항목 식별자)
        //val menuItem2 : MenuItem? = menu?.add(0,1,0, "메뉴2") // 두 번째 Int가 itemId
        menuInflater.inflate(R.menu.menu_main, menu) // 두 번째 - 어디에 적용할 것인지: 옵션메뉴에 적용하겠다 menu
        val menuSearch = menu?.findItem(R.id.menu_search)
        val searchView = menuSearch?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.tv1.text = p0
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) return true // 햄버거 버튼이 눌렸다면 true
        when (item.itemId) {
            // 코틀린코드에서추가한메뉴: 2번째 매개변수 - 메뉴 항목 식별자 // menu 리소스 xml로 추가한메뉴: 태그의 id로
            R.id.menu1 -> {
                binding.tv1.setTextColor(Color.BLUE)
                binding.tv1.setText("이게됨?")
                true
            }
            R.id.menu2 -> {

                true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}