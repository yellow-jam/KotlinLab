package com.example.ch10_notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.ch10_notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder : NotificationCompat.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  // 버전 26 이상
                val ch_id = "one-channel"
                val channel = NotificationChannel(ch_id, "My Channel One", NotificationManager.IMPORTANCE_DEFAULT)
                channel.description = "My Channel One 소개" // 알림 설명
                channel.setShowBadge(true) // 뱃지 알림
                channel.enableLights(true) // led 불빛 알림
                channel.lightColor = Color.RED  // led 색상
                channel.enableVibration(true) // 진동 알림
                channel.vibrationPattern = longArrayOf(100,200,100,200) // 진동 알림 패턴
                // (100, 200), (100, 200) : (진동 x 시간, 진동 시간) 단위 msec(밀리초)
                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audio_attr = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                channel.setSound(uri, audio_attr) // (uri, 오디오 속성)
                // 채널을 매니저에 등록
                manager.createNotificationChannel(channel)
                builder = NotificationCompat.Builder(this, ch_id)
            } else {  // 버전 25 이하
                builder = NotificationCompat.Builder(this)
            }
            // 알림 객체 설정
            builder.setSmallIcon(R.drawable.small)
            builder.setWhen(System.currentTimeMillis()) // 시각 출력(현재 시각)
            builder.setContentTitle("안녕하세요") // 알림 본문
            builder.setContentText("모바일앱프로그래밍 시간입니다.")
            // 이미지 - BitmapFactory
            val bigPic = BitmapFactory.decodeResource(resources, R.drawable.big)
            val builderStyle = NotificationCompat.BigPictureStyle() // 이미지 스타일 정의
            builderStyle.bigPicture(bigPic)
            builder.setStyle(builderStyle)

            manager.notify(11, builder.build())
        }


    }
}