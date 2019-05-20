package com.ertohru.pendonor.base

interface BaseView{
    fun toastSuccess(message:String)
    fun toastError(message:String)
    fun toastWarning(message:String)
    fun showProgress(message:String)
    fun dismissProgress()
    fun showProgressBar()
    fun dismissProgressBar()
}