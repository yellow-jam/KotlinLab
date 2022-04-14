package com.example.myapplication11

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication11.databinding.Fragment1Binding
import com.example.myapplication11.databinding.ItemRecyclerviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */

// (1) 뷰 홀더: 리사이클러 뷰 객체
class MyViewHolder(val binding : ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
// (2) 어댑터 : 항목 구성
class MyAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) //layoutInflater를 만들어야 함
        // parent는 프래그먼트가 붙어있는 액티비티(MainActivity)를 의미함
    }

    // 데이터와 뷰 홀더를 연결
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // position은 뷰 홀더가 가진 뷰 중 몇 번째인지
        val binding = (holder as MyViewHolder).binding // 타입 캐스팅 필요
        binding.itemTv.text = datas[position]
    }
}

// (4) 리사이클러 뷰 꾸미기
class MyDecoration(val context: Context) : RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        //c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.stadium), 0f, 0f, null)  // 배경 설정
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        // 스마트폰의 크기 / 2 - 이미지 크기 / 2
        val width = parent.width
        val height = parent.height

        val dr: Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val d_width = dr?.intrinsicWidth
        val d_height = dr?.intrinsicHeight

        val left = width/2 - d_width?.div(2) as Int
        val top = height/2 - d_height?.div(2) as Int

        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo), left.toFloat(), top.toFloat(), null)  // 이미지 그리기
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(10, 10, 10, 0) // 얼마나 떨어진 위치에 연결해서 사각형을 그리는가
        view.setBackgroundColor(Color.parseColor("#49c1ff"))
        ViewCompat.setElevation(view, 20.0f)
    }
}

 /* 프래그먼트 본문 */
class Fragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // 목록에 대한 정보를 만들고 MyAdapter로 전달
        val datas = mutableListOf<String>()  // MyAdapter(datas:의 타입)
        for(i in 1..9){
            datas.add("item $i")
        }

        /* (0) 프래그먼트의 뷰 바인딩 */
        val binding = Fragment1Binding.inflate(inflater, container, false)

        /* (3) 레이아웃 매니저 */
        //binding.recyclerView.layoutManager = LinearLayoutManager(activity)  // 디폴트: 수직 리스트

        val layoutManager = LinearLayoutManager(activity)
        // layoutManager.orientation = LinearLayoutManager.HORIZONTAL // 수평 리스트

        // val layoutManager = GridLayoutManager(activity, 2) // 그리드레이아웃매니저(액티비티, column)

        binding.recyclerView.layoutManager = layoutManager // (3) 레이아웃 설정
        
        binding.recyclerView.adapter = MyAdapter(datas) // (2) 어댑터 설정
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context)) // (4) 아이템데커레이션 설정

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}