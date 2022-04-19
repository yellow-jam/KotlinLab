package com.example.ch8_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.Toast
import com.example.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var pauseTime = 0L
    var initTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener{
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()
            binding.btnStart.isEnabled = false
            binding.btnStop.isEnabled = true
            binding.btnReset.isEnabled = true
        }

        binding.btnStop.setOnClickListener{
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()  // 시간 = 시작시각 - 현재시각
            binding.chronometer.stop()
            binding.btnStart.isEnabled = true
            binding.btnStop.isEnabled = false
            binding.btnReset.isEnabled = true
        }

        binding.btnReset.setOnClickListener{
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.btnStart.isEnabled = true
            binding.btnStop.isEnabled = true
            binding.btnReset.isEnabled = false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(System.currentTimeMillis()-initTime > 3000) {
                Toast.makeText(this, "종료하려면 한 번 더 누르세요", Toast.LENGTH_LONG).show()
                initTime = System.currentTimeMillis()
                return true
            }

        }

        return super.onKeyDown(keyCode, event)
    }
}


/*
package com.example.myapplication8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintSet
*/

class MainActivity8 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("mobileApp", "Action DOWN : ${event.x}, ${event.y}")
            }
            MotionEvent.ACTION_UP -> {
                Log.d("mobileApp", "Action UP : ${event.rawX}, ${event.rawY}")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("mobileApp", "Action MOVE")
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("mobileApp", "Key Down")
        when(keyCode){
            KeyEvent.KEYCODE_0 -> { Log.d("mobileApp", "0 Key Down") }
            KeyEvent.KEYCODE_B -> { Log.d("mobileApp", "B Key Down") }
            //KeyEvent.KEYCODE_BACK -> { Log.d("mobileApp", "뒤로가기 Key Down") }
            KeyEvent.KEYCODE_VOLUME_UP -> { Log.d("mobileApp", "VOLUME_UP") }
            KeyEvent.KEYCODE_VOLUME_DOWN -> { Log.d("mobileApp", "VOLUME_DOWN") }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("mobileApp", "Key Up")
        return super.onKeyUp(keyCode, event)
    }

    override fun onBackPressed() {
        Log.d("mobileApp", "뒤로가기 Key Down")
    }
}
