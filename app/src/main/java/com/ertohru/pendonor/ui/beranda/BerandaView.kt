package com.ertohru.pendonor.ui.beranda

import com.ertohru.pendonor.base.BaseView
import com.ertohru.pendonor.model.InformasiUmum

interface BerandaView : BaseView{

    fun onInformasiUmumLoaded(data:ArrayList<InformasiUmum>)

}