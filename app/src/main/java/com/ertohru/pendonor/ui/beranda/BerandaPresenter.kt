package com.ertohru.pendonor.ui.beranda

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class BerandaPresenter(private val v:BerandaView){

    fun loadInformasiUmum(){

        v.showProgressBar()

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"informasi_umum")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {

                    val data = response?.getJSONObject("result")?.getJSONArray("data")
                    val informasiUmum = ArrayList<InformasiUmumData>()
                    for(i in 0 until data?.length()!!){

                        informasiUmum.add(InformasiUmumData(
                            data.getJSONObject(i)?.getInt("id"),
                            data.getJSONObject(i)?.getString("judul"),
                            data.getJSONObject(i)?.getString("konten"),
                            data.getJSONObject(i)?.getString("rendered_konten"),
                            data.getJSONObject(i)?.getString("thumbnail"),
                            data.getJSONObject(i)?.getJSONObject("admin")?.getString("nama_lengkap")
                        ))

                    }

                    v.onInformasiUmumLoaded(informasiUmum)
                    v.dismissProgressBar()

                }

                override fun onError(anError: ANError?) {
                    Log.d("ANERROR",anError?.errorBody.toString())
                    v.dismissProgressBar()
                    v.toastError(KONEKSI_ERROR)
                }

            })

    }

}