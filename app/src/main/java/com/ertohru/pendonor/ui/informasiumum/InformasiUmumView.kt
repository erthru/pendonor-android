package com.ertohru.pendonor.ui.informasiumum

import com.ertohru.pendonor.base.BaseView

interface InformasiUmumView: BaseView {

    fun onInformasiUmumLoaded(data:ArrayList<InformasiUmumData>?)
    fun onInformasiUmumNextLoaded(data:ArrayList<InformasiUmumData>?)
    fun showProgressBottom()
    fun dismissProgressBottom()

}