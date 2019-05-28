package com.ertohru.pendonor.ui.pendonordetail

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class PendonorDetailPresenter(val v:PendonorDetailView){

    fun loadPendonorDetail(id:String){

        v.showProgress("Memuat data pendonor...")

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"pendonor/"+id)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    v.dismissProgress()

                    val data = HashMap<String, String>()
                    data["nama_lengkap"] = response?.getJSONObject("result")?.getString("nama_lengkap")!!
                    data["telp"] = response?.getJSONObject("result")?.getString("telp")!!
                    data["alamat"] = response?.getJSONObject("result")?.getString("alamat")!!
                    data["jenkel"] = response?.getJSONObject("result")?.getString("jenkel")!!
                    data["golongan_darah"] = response?.getJSONObject("result")?.getString("golongan_darah")!!
                    data["resus"] = response?.getJSONObject("result")?.getString("resus")!!
                    v.dataPendonorLoaded(data)

                }

                override fun onError(anError: ANError?) {
                    v.dismissProgress()
                    v.toastError(KONEKSI_ERROR)
                    v.onDataPendonorFailed()
                }

            })

    }

    fun loadPendonorTerakhirDonor(id:String){

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"riwayat/pendonor/"+id)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    v.dismissProgress()

                    val data = HashMap<String, String>()
                    if(response?.getJSONObject("result")?.getJSONArray("data")?.length() == 0){
                        data["tgl_donor"] = "-"
                    }else{
                        data["tgl_donor"] = response?.getJSONObject("result")?.getJSONArray("data")?.getJSONObject(0)?.getString("tgl_donor")!!
                    }
                    v.dataPendonorTerakhirDonor(data)

                }

                override fun onError(anError: ANError?) {
                    v.dismissProgress()
                    v.toastError(KONEKSI_ERROR)
                    v.onDataPendonorTerakhirDonorFailed()
                }

            })

    }

}