package com.ertohru.pendonor.ui.mappendonor

import com.ertohru.pendonor.base.BaseView
import com.ertohru.pendonor.model.Pendonor
import com.ertohru.pendonor.model.PendonorWithDistance
import com.google.android.gms.maps.model.PolylineOptions

interface MapPendonorView:BaseView{

    fun onPendonorLoaded(data:ArrayList<PendonorWithDistance>?)
    fun onPendonorDataEmpty()
    fun onPolyDrawed(poly: PolylineOptions)

}