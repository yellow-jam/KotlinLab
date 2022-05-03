package com.example.myapplication16

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.myapplication16.databinding.ActivityMainBinding
import java.io.File
import java.lang.Exception
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var filePath : String  // 카메라 연동 방법(2)의 파일 경로 선언
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* 주소록 앱 연동 */
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

        /* 갤러리 앱 연동 */
        // 사후 처리 메소드
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                try{
                    val calRatio = calculateInSampleSize(
                        it.data!!.data!!, 150, 150
                    )
                    val option = BitmapFactory.Options()  // decodeStream의 3번째 인자 opts: 상수
                    // option.inSampleSize = 4
                    var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                    inputStream!!.close()
                    inputStream = null

                    bitmap?.let{
                        binding.userIdImg.setImageBitmap(bitmap)
                    } ?: let{
                        Log.d("mobileApp", "bitmap null")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        binding.galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        // 16-2 카메라 앱 연동하기 - 방법 (1) 데이터로 가져오기
        val requestCameraThumbnailLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            val bitmap = it?.data?.extras?.get("data") as Bitmap
            binding.userIdImg.setImageBitmap(bitmap)
        }

        binding.cameraBtn1.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            requestCameraThumbnailLauncher.launch(intent)
        }

        // 16-2 카메라 앱 연동하기 - 방법 (2) 파일로 공유하기
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult() ){
            val calRatio = calculateInSampleSize(Uri.fromFile(File(filePath)), 150, 150)
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio
            val bitmap = BitmapFactory.decodeFile(filePath, option)
            bitmap?.let{
                binding.userIdImg.setImageBitmap(bitmap)
            } ?:let{
                Log.d("mobileApp", "bitmap null")
            }
        }
        val timeS:String = SimpleDateFormat("yyyymmdd_HHmmss").format(Date())
        val storeDir:File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timeS}_", ".jpeg", storeDir) // 3번째 인자: 저장할 곳
        filePath = file.absolutePath  // 파일 경로!! 선언은 MainActivity 상단에
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.example.myapplication16.FileProvider", // 매니페스트 파일 <provider> 태그의 authorities 속성값
            file
        )

        binding.cameraBtn2.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)  // 두 번째 인자: URI
            requestCameraFileLauncher.launch(intent)
        }

        /* 지도 앱 연동 */
        binding.mapBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo: 37.562952, 126.9779451"))  // 지도 프로토콜 geo - 위치정보 위도, 경도
            startActivity(intent)
        }
        

    }

    // 저자의 메소드: 주어진 Uri의 크기를 reqWidth, reqHeight로 줄이기 위한 옵션비율값(inSampleSize)을 계산
    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}