package com.example.myapplication14

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val receiver = MyReceiver()
        val filter = IntentFilter("ACTION_RECEIVER").apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(receiver, filter)  // (리시버, 필터)
         */

        registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))!!.apply {
            val status = getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            if(status == BatteryManager.BATTERY_STATUS_CHARGING){
                val chargePlug = getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
                when(chargePlug){
                    BatteryManager.BATTERY_PLUGGED_AC -> Log.d("mobileApp", "AC CHARGED")
                    BatteryManager.BATTERY_PLUGGED_USB -> Log.d("mobileApp", "USB CHARGED")
                }
            }
            else {
                Log.d("mobileApp", "DISCHARGING")
            }
        }
        binding.button.setOnClickListener {
            val intent = Intent(this, MyReceiver::class.java)
            sendBroadcast(intent)
        }
    }
}