package com.ertohru.pendonor.ui.profil

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject
import java.io.File

class ProfilPresenter(private val v:ProfilView){

    fun loadProfil(id:String){

        v.showProgressBar()

        AndroidNetworking.get(ApiEndPoint.PENDONOR+"pengguna/"+id)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    v.dismissProgressBar()

                    val data = HashMap<String, String>()
                    data["nama_lengkap"] = response?.getJSONObject("result")?.getString("nama_lengkap")!!
                    data["email"] = response?.getJSONObject("result")?.getString("email")!!
                    data["foto"] = response?.getJSONObject("result")?.getString("foto")!!

                    v.onProfilLoaded(data)

                }

                override fun onError(anError: ANError?) {
                    v.dismissProgressBar()
                    Log.d("ANERROR",anError?.errorBody?.toString())
                    v.onProfilFailed()
                }

            })

    }

    fun gantiPassword(id:String, password:String){

        v.showProgress("Mohon tunggu...")

        AndroidNetworking.put(ApiEndPoint.PENDONOR+"pengguna/password/"+id)
            .addBodyParameter("password",password)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    v.dismissProgress()
                    v.toastSuccess("Password diganti")
                    v.onPasswordChanged()
                }

                override fun onError(anError: ANError?) {
                    v.dismissProgress()
                    v.toastError(KONEKSI_ERROR)
                    Log.d("ANERROR",anError?.errorBody?.toString())
                }
            })

    }

    fun gantiFoto(id:String, foto: File){

        v.showProgress("Mengganti Foto Profil...")

        AndroidNetworking.upload(ApiEndPoint.PENDONOR+"pengguna/foto/"+id)
            .addMultipartFile("foto",foto)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    v.dismissProgress()
                    v.toastSuccess("Foto diganti")
                    v.onFotoUpdated()
                }

                override fun onError(anError: ANError?) {
                    v.dismissProgress()
                    v.toastError(KONEKSI_ERROR)
                    Log.d("ANERROR",anError?.errorBody?.toString())
                }

            })

    }

    fun updateProfil(id:String, namaLengkap:String, email:String){

        v.showProgress("Memperbarui Profil...")

        AndroidNetworking.put(ApiEndPoint.PENDONOR+"pengguna/"+id)
            .addBodyParameter("nama_lengkap",namaLengkap)
            .addBodyParameter("email",email)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    v.dismissProgress()

                    val error = response?.getBoolean("error")
                    if(error!!){
                        v.toastError("Email telah digunakan")
                    }else{
                        v.toastSuccess("Profil diperbarui")
                        v.onProfilUpdated()
                    }
                }

                override fun onError(anError: ANError?) {
                    v.dismissProgress()
                    v.toastError(KONEKSI_ERROR)
                    Log.d("ANERROR",anError?.errorBody?.toString())
                }

            })

    }

}