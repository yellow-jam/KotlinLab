package com.example.myapplication14

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        //TODO("MyReceiver.onReceive() is not implemented")
        when(intent.action){
            Intent.ACTION_BATTERY_LOW -> Log.d("mobileApp", "BATTERY_LOW")
            Intent.ACTION_BATTERY_OKAY -> Log.d("mobileApp", "ACTION_BATTERY_OKAY")
            Intent.ACTION_BATTERY_CHANGED -> Log.d("mobileApp", "ACTION_BATTERY_CHANGED")
            Intent.ACTION_POWER_CONNECTED -> Log.d("mobileApp", "ACTION_POWER_CONNECTED")
            Intent.ACTION_POWER_DISCONNECTED -> Log.d("mobileApp", "ACTION_POWER_DISCONNECTED")
        }

    }
}