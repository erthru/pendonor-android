package com.ertohru.pendonor.ui.informasiumum

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class InformasiUmumPresenter(private val v:InformasiUmumView){

    fun loadInformasiUmum(){

        v.showProgressBar()

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"informasi_umum")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {

                    v.dismissProgressBar()

                    val data = ArrayList<InformasiUmumData>()
                    val jsonArrayData = response?.getJSONObject("result")?.getJSONArray("data")

                    for(i in 0 until jsonArrayData?.length()!!){
                        data.add(InformasiUmumData(
                            jsonArrayData.getJSONObject(i).getInt("id"),
                            jsonArrayData.getJSONObject(i).getString("judul"),
                            jsonArrayData.getJSONObject(i).getString("konten"),
                            jsonArrayData.getJSONObject(i).getString("rendered_konten"),
                            jsonArrayData.getJSONObject(i).getString("thumbnail"),
                            jsonArrayData.getJSONObject(i).getJSONObject("admin").getString("nama_lengkap")
                        ))
                    }

                    InformasiUmumActivity.CURRENT_PAGE = response?.getJSONObject("result")?.getInt("current_page")!!
                    InformasiUmumActivity.LAST_PAGE = response?.getJSONObject("result")?.getInt("last_page")!!
                    InformasiUmumActivity.ON_LAST_PAGE = InformasiUmumActivity.CURRENT_PAGE == InformasiUmumActivity.LAST_PAGE

                    v.onInformasiUmumLoaded(data)

                }

                override fun onError(anError: ANError?) {
                    Log.d("ANERROR",anError?.errorBody.toString())
                    v.toastError(KONEKSI_ERROR)
                    v.dismissProgressBar()
                }

            })

    }

}