package com.ertohru.pendonor.ui.stokdarah

import com.ertohru.pendonor.base.BaseView

interface StokDarahView: BaseView {

    fun onStokDarahLoaded(data:HashMap<String,String>)
    fun onStokDarahFailedToLoad()

}