package com.ertohru.pendonor.ui.mappendonor

import com.ertohru.pendonor.base.BaseView
import com.ertohru.pendonor.model.Pendonor

interface MapPendonorView:BaseView{

    fun onPendonorLoaded(data:ArrayList<Pendonor>?)
    fun onPendonorDataEmpty()

}