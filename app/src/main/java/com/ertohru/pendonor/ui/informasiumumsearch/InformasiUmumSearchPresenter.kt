package com.ertohru.pendonor.ui.informasiumumsearch

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.model.InformasiUmum
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class InformasiUmumSearchPresenter(val v:InformasiUmumSearchView){

    fun loadSearch(q:String){

        v.showProgressBar()

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"informasi_umum/cari/query")
            .addQueryParameter("q",q)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {

                    v.dismissProgressBar()

                    val data = ArrayList<InformasiUmum>()
                    val jsonArrayData = response?.getJSONArray("result")

                    for(i in 0 until jsonArrayData?.length()!!){
                        data.add(InformasiUmum(
                            jsonArrayData.getJSONObject(i).getInt("id"),
                            jsonArrayData.getJSONObject(i).getString("judul"),
                            jsonArrayData.getJSONObject(i).getString("konten"),
                            jsonArrayData.getJSONObject(i).getString("rendered_konten"),
                            jsonArrayData.getJSONObject(i).getString("thumbnail"),
                            jsonArrayData.getJSONObject(i).getJSONObject("admin").getString("nama_lengkap")
                        ))
                    }

                    v.onSearchLoaded(data)

                }

                override fun onError(anError: ANError?) {
                    Log.d("ANERROR",anError?.errorBody.toString())
                    v.toastError(KONEKSI_ERROR)
                    v.dismissProgressBar()
                    v.onSearchFailed()
                }

            })

    }

}