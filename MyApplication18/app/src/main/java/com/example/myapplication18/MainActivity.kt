package com.example.myapplication18

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.example.myapplication18.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        // permission 사용자 허가
        checkPermissions()


        // 폰 정보
        binding.tv.text = getPhoneInfo()

        // 연결 정보


    }

    private fun checkPermissions(){
        val REQUEST_CODE = 1001  // int 타입
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){ // 30 이상
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_NUMBERS), REQUEST_CODE)
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){  // 29 이하 24 이상
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_CODE)
        }
    }

    private fun getPhoneInfo() : String {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED
          || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){

            val manager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val countryIso = manager.networkCountryIso  // 네트워크 제공 국가
            val operatorName = manager.networkOperatorName  // 네트워크 제공 사업자
            val phoneNum = manager.line1Number  // 스마트폰의 전화번호

            return "contryIso: $countryIso\noperatorName: $operatorName\nphoneNum: $phoneNum"
        }
        return ""
    }
}