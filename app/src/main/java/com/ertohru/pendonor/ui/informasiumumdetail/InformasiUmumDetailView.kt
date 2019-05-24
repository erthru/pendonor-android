package com.ertohru.pendonor.ui.informasiumumdetail

import com.ertohru.pendonor.base.BaseView

interface InformasiUmumDetailView: BaseView{
    fun onDetailLoaded(data:HashMap<String,String>)
    fun onDetailFailed()
}
