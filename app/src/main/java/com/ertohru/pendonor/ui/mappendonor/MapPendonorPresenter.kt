package com.ertohru.pendonor.ui.mappendonor

import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONArray
import org.json.JSONObject

class MapPendonorPresenter(val v:MapPendonorView){

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
                        val data = ArrayList<PendonorData>()
                        for(i in 0 until response?.getJSONArray("result")?.length()!!) {
                            data.add(PendonorData(
                                response.getJSONArray("result")?.getJSONObject(i)?.getInt("id").toString(),
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("nama_lengkap")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("alamat")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("telp")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("jenkel")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("tgl_lahir")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("golongan_darah")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("resus")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("lat")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("lng")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("created_at")!!,
                                response.getJSONArray("result")?.getJSONObject(i)?.getString("updated_at")!!
                            ))
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

}