package com.ertohru.pendonor.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefPengguna(val context: Context){

    val sp:SharedPreferences = context.getSharedPreferences("pengguna",0)
    val editor:SharedPreferences.Editor = sp.edit()

    fun set(id:String){
        editor.putString("id",id)
        editor.putBoolean("isLogin",true)
        editor.commit()
    }

    fun id() : String? = sp.getString("id",null)

    fun isLogin() : Boolean? = sp.getBoolean("isLogin",false)

    fun destroy(){
        editor.clear()
        editor.commit()
    }

}