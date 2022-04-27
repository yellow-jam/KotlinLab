package com.example.myapplication16

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication16.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 사후 처리 메소드
        val requestContractLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode == RESULT_OK){
                    Log.d("mobileApp", "${it.data?.data}")
                    val cursor = contentResolver.query(
                        it!!.data!!.data!!,
                        arrayOf<String>(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER),
                        null,null,null
                    )
                    if(cursor!!.moveToFirst()){  // 가지고 온 결과가 여러 개일 수 있는데 그중 첫 번째로 이동하겠다
                        val name = cursor?.getString(0)
                        val phone = cursor?.getString(1)
                        binding.addrTv.text = "이름 : $name, 전화번호 : $phone"
                    }
                }
            }
        binding.addrBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            requestContractLauncher.launch(intent)
        }
    }
}