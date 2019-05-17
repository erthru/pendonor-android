package com.ertohru.pendonor.ui.login

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import org.json.JSONObject

class LoginPresenter(val v:LoginView){

    fun auth(email:String, password:String){

        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            v.toastError("Lengkapi data.")
        }else{

            v.showProgress("Mohon tunggu...")

            AndroidNetworking.post(ApiEndPoint.PENDONOR+"pengguna/login")
                    .addBodyParameter("email",email)
                    .addBodyParameter("password",password)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(object: JSONObjectRequestListener{
                        override fun onResponse(response: JSONObject?) {

                            v.dismissProgress()

                            if(!response?.getBoolean("error")!!){
                                v.onLoginSuccess(response?.getJSONObject("result").getString("id"))
                            }else{
                                v.toastError("login gagal.")
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