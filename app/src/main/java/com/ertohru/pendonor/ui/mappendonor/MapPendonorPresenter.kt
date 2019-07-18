package com.ertohru.pendonor.ui.mappendonor

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.model.Pendonor
import com.ertohru.pendonor.model.PendonorWithDistance
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import com.ertohru.pendonor.utils.Loading
import com.ertohru.pendonor.utils.Polyline
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONObject

class MapPendonorPresenter(val context: Context, val v:MapPendonorView){

    fun loadPendonor(golonganDarah:String, lat:String, lng:String){

        v.showProgress("Memuat data pendonor...")

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"pendonor/by/data")
            .addQueryParameter("golongan_darah",golonganDarah)
            .addQueryParameter("lat",lat)
            .addQueryParameter("lng",lng)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {

                    v.dismissProgress()

                    if(response?.getJSONArray("result")?.length() != 0){
                        val data = ArrayList<PendonorWithDistance>()
                        for(i in 0 until response?.getJSONArray("result")?.length()!!) {
                            data.add(
                                PendonorWithDistance(
                                    response.getJSONArray("result")?.getJSONObject(i)?.getInt("id").toString(),
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("nama_lengkap")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("alamat")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("telp")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("jenkel")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("golongan_darah")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("resus")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("umur")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("pekerjaan")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("lat")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("lng")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("created_at")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("updated_at")!!,
                                    response.getJSONArray("result")?.getJSONObject(i)?.getString("distance")!!
                                )
                            )
                        }
                        v.onPendonorLoaded(data)
                    }else{
                        v.toastError("Tidak ada data yang tersedia")
                        v.onPendonorDataEmpty()
                    }


                }

                override fun onError(anError: ANError?) {
                    v.dismissProgress()
                    Log.d("ANERROR",anError?.errorBody.toString())
                    v.toastError(KONEKSI_ERROR)
                }

            })

    }

    fun addSearchHistory(golonganDarah: String){

        AndroidNetworking.post(ApiEndPoint.PENDONOR+"search_history")
            .addBodyParameter("golongan_darah",golonganDarah)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    Log.d("ON_RESPONSE",response.toString())
                }

                override fun onError(anError: ANError?) {
                    Log.d("ANERROR",anError?.errorBody.toString())
                }

            })

    }

    fun drawPoly(origin: String, destination: String){

        val loading = Loading(context)
        loading.create("Mencari rute terdekat...")

        AndroidNetworking.get("https://maps.googleapis.com/maps/api/directions/json?origin=$origin&destination=$destination&sensor=false&key=AIzaSyBsKurseekHERPXhnmngSSzS0USrRHymk4")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{

                override fun onResponse(response: JSONObject?) {

                    val routes = response?.getJSONArray("routes")
                    val poly = routes?.getJSONObject(0)?.getJSONObject("overview_polyline")?.getString("points")

                    val pontos = Polyline.decodePoly(poly!!)

                    Log.d("RESPONSE_DIRECTION", response.toString())

                    for(i in 0 until pontos.size - 1){

                        val src = pontos[i]
                        val dest = pontos[i + 1]

                        v.onPolyDrawed(PolylineOptions().add(LatLng(src.latitude, src.longitude), LatLng(dest.latitude, dest.longitude)).width(5f).color(
                            Color.RED).geodesic(true))

                    }

                    loading.dismiss()

                }

                override fun onError(anError: ANError?) {

                    v.toastError(KONEKSI_ERROR)

                }

            })

    }


}