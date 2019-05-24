package com.ertohru.pendonor.ui.informasiumumdetail

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class InformasiUmumDetailPresenter(val v:InformasiUmumDetailView){

    fun loadDetail(id:String){

        v.showProgressBar()

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"informasi_umum/"+id)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    val data = HashMap<String, String>()
                    data["judul"] = response?.getJSONObject("result")?.getString("judul")!!
                    data["updated_at"] = response?.getJSONObject("result")?.getString("updated_at")!!
                    data["thumbnail"] = response?.getJSONObject("result")?.getString("thumbnail")!!
                    data["penulis"] = response?.getJSONObject("result")?.getJSONObject("admin")?.getString("nama_lengkap")!!
                    data["konten"] = response?.getJSONObject("result")?.getString("konten")!!
                    v.onDetailLoaded(data)
                }

                override fun onError(anError: ANError?) {
                    v.toastError(KONEKSI_ERROR)
                    Log.d("ANERROR",anError?.errorBody.toString())
                    v.onDetailFailed()
                }

            })

    }

}