package com.ertohru.pendonor.ui.main

import com.ertohru.pendonor.base.BaseView

interface MainView:BaseView{

    fun onUserDataLoaded(data:HashMap<String, String>)
    fun onUserDataFailed()

}