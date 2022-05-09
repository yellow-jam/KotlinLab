package com.example.myapplication18

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
        setContentView(binding.root)

        // permission 사용자 허가
        checkPermissions()
        // 폰 정보
        binding.tv.text = getPhoneInfo()
        // 연결 정보
        binding.tv.text = binding.tv.text.toString() + getConnectivity()


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

    // 왜 연결되지 않았는가를 텍스트뷰에 출력 : String 타입으로
    private fun getConnectivity() : String {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){  // 24 이상
            val nw = manager.activeNetwork
            if(nw == null) return "activeNetwork NULL"
            val actNw = manager.getNetworkCapabilities(nw)  // 네트워크 용량 확인
            if(actNw == null) return "activeNetwork - Capabilities NULL"  // 네트워크는 있는데 용량이 없는 경우

            // 네트워크 상태(유형) 확인
            if(actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return "cellular available"
            else if(actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return "wifi available"
            else return "available"
        } else {  // 24 이하에서는 연결되었는지 안되었는지만 판단
            if(manager.activeNetworkInfo!!.isConnected) return "activeNetworkInfo is Connected"
            return "activeNetworkInfo is NOT Connected"
        }
        return ""
    }
}