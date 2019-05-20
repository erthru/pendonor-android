package com.ertohru.pendonor.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ertohru.pendonor.R
import com.ertohru.pendonor.utils.Loading
import es.dmoral.toasty.Toasty

abstract class BaseFragment : Fragment(),BaseView {

    lateinit var loading: Loading
    lateinit var progressBar: ProgressBar
    lateinit var v:View

    var progressOnShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = Loading(context!!)
    }

    override fun toastSuccess(message:String){
        Toasty.success(context!!,message, Toasty.LENGTH_SHORT).show()
    }

    override fun toastError(message:String){
        Toasty.error(context!!,message, Toasty.LENGTH_SHORT).show()
    }

    override fun toastWarning(message:String){
        Toasty.warning(context!!,message, Toasty.LENGTH_SHORT).show()
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

    override fun showProgressBar(){
        this.progressBar.visibility = View.VISIBLE
    }

    override fun dismissProgressBar(){
        this.progressBar.visibility = View.GONE
    }
}
