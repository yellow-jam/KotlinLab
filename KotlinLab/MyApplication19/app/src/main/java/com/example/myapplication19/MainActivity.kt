package com.example.myapplication19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var googleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0

        // googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE  // 위성 지도 모드
        // 지도가 표시되면 보여지는 지역 설정 (위도, 경도)
        val latLng = LatLng(37.568256, 126.897240)
        val position: CameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        // 카메라 이동
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))
        // 마커 추가
        val markerOp = MarkerOptions()
        markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOp.position(latLng)
        markerOp.title("월드컵경기장")
        googleMap?.addMarker(markerOp)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(0, 11111, 0, "위성지도")
        menu?.add(0, 22222, 0, "일반지도")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            11111 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            22222 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            }

        }
        return false
    }
}