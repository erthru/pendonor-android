package com.ertohru.pendonor.ui.beranda

import com.ertohru.pendonor.base.BaseView

interface BerandaView : BaseView{

    fun onInformasiUmumLoaded(data:ArrayList<InformasiUmumData>)

}