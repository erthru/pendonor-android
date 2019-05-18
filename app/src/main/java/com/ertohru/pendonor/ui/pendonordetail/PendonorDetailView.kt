package com.ertohru.pendonor.ui.pendonordetail

import com.ertohru.pendonor.base.BaseView

interface PendonorDetailView: BaseView{

    fun dataPendonorLoaded(data: HashMap<String, String>)
    fun onDataPendonorFailed()

}