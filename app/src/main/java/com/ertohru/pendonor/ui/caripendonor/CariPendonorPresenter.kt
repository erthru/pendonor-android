package com.ertohru.pendonor.ui.caripendonor

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.google.android.gms.location.LocationServices
import org.json.JSONObject

class CariPendonorPresenter(val context: Context, val v:CariPendonorView){

    @SuppressLint("MissingPermission")
    fun loadUserCurrentLocation(){

        v.showProgress("Memuat lokasi pengguna...")

        val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
        fusedLocation.lastLocation.addOnSuccessListener {
            val lat = it.latitude.toString()
            val lng = it.longitude.toString()
            fetchLocation(lat,lng)
        }
    }

    private fun fetchLocation(lat:String, lng:String){
        AndroidNetworking.get(ApiEndPoint.PENDONOR+"external/geocoding/reverse")
            .addQueryParameter("lat",lat)
            .addQueryParameter("lng",lng)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {

                    v.dismissProgress()

                    if(response?.getJSONArray("results")?.length() != 0){
                        val locationName = response?.getJSONArray("results")?.getJSONObject(0)?.getString("formatted_address")
                        val data = HashMap<String, String>()
                        data["location_name"] = locationName!!
                        CariPendonorActivity.LAT = lat
                        CariPendonorActivity.LNG = lng
                        v.onUserCurrentLocationLoaded(data)
                    }else{
                        v.toastError("Gagal mengambil lokasi pengguna, silahkan ganti secara manual")
                        Log.d("RESPONSE",response.toString())
                    }

                }

                override fun onError(anError: ANError?) {
                    v.toastError("Gagal mengambil lokasi pengguna, silahkan ganti secara manual")
                    v.dismissProgress()
                }
            })
    }

}
