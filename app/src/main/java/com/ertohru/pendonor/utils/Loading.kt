package com.ertohru.pendonor.utils

import android.content.Context
import android.view.LayoutInflater
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.ertohru.pendonor.R
import kotlinx.android.synthetic.main.dialog_loading.view.*

class Loading(val context: Context){

    var isShown = false
    lateinit var loading:MaterialDialog

    fun create(message:String){

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading,null,false)
        view.lbLoading.text = message
        loading = MaterialDialog(context).show {
            customView(view = view)
        }
        loading.cornerRadius(6f)
        isShown = true

    }

    fun dismiss(){
        if(isShown) {
            loading.dismiss()
            isShown = false
        }
    }

}