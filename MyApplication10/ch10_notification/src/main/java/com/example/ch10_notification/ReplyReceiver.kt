package com.example.ch10_notification

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        //TODO("ReplyReceiver.onReceive() is not implemented")

        val replyTxt = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply") // Main 69행 result_key
        Log.d("mobileApp", "$replyTxt")
        val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        /* getSystemService 메소드는 액티비티에서는 바로 사용가능한데,
          이 컴포넌트는 브로드캐스트 리시버임.
           context.getSystemService() 형태로 사용
         */
        manager.cancel(11)
    }
}