package com.ertohru.pendonor.ui.informasiumum

import com.ertohru.pendonor.base.BaseView
import com.ertohru.pendonor.model.InformasiUmum

interface InformasiUmumView: BaseView {

    fun onInformasiUmumLoaded(data:ArrayList<InformasiUmum>?)
    fun onInformasiUmumNextLoaded(data:ArrayList<InformasiUmum>?)
    fun showProgressBottom()
    fun dismissProgressBottom()

}