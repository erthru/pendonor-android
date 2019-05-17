package com.ertohru.pendonor.ui.caripendonor

import com.ertohru.pendonor.base.BaseView

interface CariPendonorView:BaseView{

    fun onUserCurrentLocationLoaded(data:HashMap<String, String>)

}