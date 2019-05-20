package com.ertohru.pendonor.ui.main

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class MainPresenter(val v:MainView){

    fun loadDataUser(id:String){

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"pengguna/"+id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        val data = HashMap<String,String>()
                        data["nama_lengkap"] = response?.getJSONObject("result")?.getString("nama_lengkap")!!
                        data["email"] = response?.getJSONObject("result")?.getString("email")!!
                        data["foto"] = response?.getJSONObject("result")?.getString("foto")!!
                        v.onUserDataLoaded(data)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d("ANERROR",anError?.errorBody.toString())
                        v.toastError(KONEKSI_ERROR)
                        v.onUserDataFailed()
                    }
                })

    }

}