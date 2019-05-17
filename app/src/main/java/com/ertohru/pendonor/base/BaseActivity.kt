package com.ertohru.pendonor.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.ertohru.pendonor.R
import com.ertohru.pendonor.utils.Loading
import es.dmoral.toasty.Toasty

abstract class BaseActivity : AppCompatActivity(),BaseView {

    lateinit var loading: Loading
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = Loading(this)
    }

    override fun toastSuccess(message:String){
        Toasty.success(this,message, Toasty.LENGTH_SHORT).show()
    }

    override fun toastError(message:String){
        Toasty.error(this,message, Toasty.LENGTH_SHORT).show()
    }

    override fun toastWarning(message:String){
        Toasty.warning(this,message, Toasty.LENGTH_SHORT).show()
    }

    override fun showProgress(message:String){
        loading.create(message)
    }

    override fun dismissProgress(){
        loading.dismiss()
    }

    fun initProgressBar(progressBar:ProgressBar){
        this.progressBar = progressBar
    }

    fun showProgressBar(){
        this.progressBar.visibility = View.VISIBLE
    }

    fun dismissProgressBar(){
        this.progressBar.visibility = View.GONE
    }
}
