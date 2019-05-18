package com.ertohru.pendonor.ui.mappendonor

import com.ertohru.pendonor.base.BaseView

interface MapPendonorView:BaseView{

    fun onPendonorLoaded(data:ArrayList<PendonorData>?)
    fun onPendonorDataEmpty()

}