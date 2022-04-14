package com.example.myapplication10

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapplication10.databinding.ActivityMainBinding
import com.example.myapplication10.databinding.DialogInputBinding

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button1.setOnClickListener {
            //Toast.makeText(this, "첫번째 버튼의 토스트입니다", Toast.LENGTH_LONG).show()
            val toast = Toast.makeText(this, "첫번째 버튼의 토스트입니다", Toast.LENGTH_LONG)
            toast.setText("수정된 토스트입니다.")
            toast.duration = Toast.LENGTH_SHORT
            // toast.setGravity(Gravity.TOP, 20, 20) // API 30 이후는 지원하지 않음
            toast.addCallback(

                object: Toast.Callback() {
                    override fun onToastHidden() {
                        super.onToastHidden()
                        Log.d("mobileApp", "토스트가 사라집니다")
                    }
                    override fun onToastShown() {
                        super.onToastShown()
                        Log.d("mobileApp", "토스트가 나타납니다")
                    }
                }
            )
            toast.show()
        }

        binding.button2.setOnClickListener {
            DatePickerDialog(this,
                object:DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        // p1 연, p2 월, p3 일
                        // TODO("Not yet implemented")
                        Log.d("mobileApp", "$p1 년, ${p2+1} 월, $p3 일")
                    }
                },
                2022, 2, 30).show() // month는 0~11로 인식
        }

        binding.button3.setOnClickListener {
            TimePickerDialog(this,
                object:TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                        // TODO("Not yet implemented")
                        Log.d("mobileApp", "$p1 시, $p2 분")
                    }
                }
                , 13, 0, true).show() // true 24시간제, false 12시간제
        }

        // 3. 대화상자 띄우기 (4,5,6,7)

        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1==DialogInterface.BUTTON_POSITIVE) {
                    Log.d("mobileApp", "positive button")
                }
                else if (p1==DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("mobileApp", "negative button")
                }
            }
        }

        // 버튼4 - 대화상자
        binding.button4.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("알림창 테스트")
                setIcon(android.R.drawable.ic_dialog_info)
                setMessage("정말 종료하시겠습니까?")
                setPositiveButton("YES", eventHandler)
                setNegativeButton("NO", eventHandler)
                setNeutralButton("MORE", null)
                setCancelable(false) // true: 뒤로가기 버튼으로 창 사라지게 하기 허용
                show()
            }.setCanceledOnTouchOutside(false) // true: 창 바깥 터치 시 창 사라지게 하기 허용
        }

        // 버튼5 - 선택목록 대화상자
        val items = arrayOf<String>("사과", "딸기", "복숭아", "토마토")
        binding.button5.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("아이템 목록 선택")
                setIcon(android.R.drawable.ic_dialog_info)
                setItems(items, object:DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        // p1: items 배열에서 선택된 항목의 인덱스
                        Log.d("mobileApp", "${items[p1]}")
                    }
                })
                setPositiveButton("닫기", null)
                show()
            }
        }

        // 버튼6 - 체크박스 대화상자
        binding.button6.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("멀티 아이템 목록 선택")
                setIcon(android.R.drawable.ic_dialog_info)
                setMultiChoiceItems(items, booleanArrayOf(false, true, false, false),
                    object:DialogInterface.OnMultiChoiceClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                            // p0 알림창에 대한 인터페이스, p2 선택된 아이템의 인덱스, p3 지금 클릭에 의해 선택되었는가
                            // TODO("Not yet implemented")
                            Log.d("mobileApp", "${items[p1]} ${if(p2) "선택" else "해제" }")
                        }
                    }
                    )
                setPositiveButton("닫기", null)
                show()
            }
        }

        // 버튼7 - 라디오버튼 대화상자
        binding.button7.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("싱글 아이템 목록 선택")
                setIcon(android.R.drawable.ic_dialog_info)
                setSingleChoiceItems(items, 1, object:DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Log.d("mobileApp", "${items[p1]}")
                    }
                })
                setPositiveButton("닫기", null)
                show()
            }
        }

        // 버튼8 - 커스텀 대화상자
        val dialogBinding = DialogInputBinding.inflate(layoutInflater)
        val alert = AlertDialog.Builder(this)
            .setTitle("입력")
            .setView(dialogBinding.root)
            .setPositiveButton("닫기", null)
            .create()
        binding.button8.setOnClickListener {
            alert.show()
        }

    }
}