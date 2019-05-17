package com.ertohru.pendonor.ui.register

import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class RegisterPresenter (private val v:RegisterView){

    fun register(namaLengkap:String, email:String, password:String, passwordRe:String){

        if(namaLengkap.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty())
            v.toastError("Lengkapi data.")
        else if (password != passwordRe)
            v.toastError("Password tidak sama")
        else{

            v.showProgress("Mohon tunggu...")

            AndroidNetworking.post(ApiEndPoint.PENDONOR+"pengguna")
                    .addBodyParameter("nama_lengkap",namaLengkap)
                    .addBodyParameter("email",email)
                    .addBodyParameter("password",password)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(object :JSONObjectRequestListener{
                        override fun onResponse(response: JSONObject?) {

                            v.dismissProgress()

                            if(response?.getBoolean("error")!!){
                                v.toastError("Email telah terdaftar")
                            }else{
                                v.toastSuccess("Registrasi berhasil.")
                                v.onRegisterSuccess()
                            }
                        }

                        override fun onError(anError: ANError?) {
                            v.dismissProgress()
                            v.toastError(KONEKSI_ERROR)
                            Log.d("ANERROR",anError?.errorBody.toString())
                        }

                    })
        }

    }

}