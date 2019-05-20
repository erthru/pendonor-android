package com.ertohru.pendonor.ui.stokdarah

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class StokDarahPresenter(val v:StokDarahView) {

    fun loadStokDarah(){

        v.showProgress("Memuat data pendonor...")

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"custom/stok/darah")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {

                    val map = HashMap<String,String>()
                    map["pendonor_total"] = response?.getJSONObject("data")?.getJSONObject("pendonor")?.getString("total")!!
                    map["bisa_donor_total"] = response?.getJSONObject("data")?.getJSONObject("bisa_donor")?.getString("total")!!
                    map["bisa_donor_a_total"] = response?.getJSONObject("data")?.getJSONObject("bisa_donor_a")?.getString("total")!!
                    map["bisa_donor_b_total"] = response?.getJSONObject("data")?.getJSONObject("bisa_donor_b")?.getString("total")!!
                    map["bisa_donor_ab_total"] = response?.getJSONObject("data")?.getJSONObject("bisa_donor_ab")?.getString("total")!!
                    map["bisa_donor_o_total"] = response?.getJSONObject("data")?.getJSONObject("bisa_donor_o")?.getString("total")!!

                    v.onStokDarahLoaded(map)

                    v.dismissProgress()

                }

                override fun onError(anError: ANError?) {
                    Log.d("ANERROR",anError?.errorBody.toString())
                    v.dismissProgress()
                    v.toastError(KONEKSI_ERROR)
                    v.onStokDarahFailedToLoad()
                }
            })

    }

}