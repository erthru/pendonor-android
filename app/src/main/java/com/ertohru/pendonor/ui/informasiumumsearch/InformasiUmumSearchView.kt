package com.ertohru.pendonor.ui.informasiumumsearch

import com.ertohru.pendonor.base.BaseView
import com.ertohru.pendonor.model.InformasiUmum

interface InformasiUmumSearchView: BaseView {

    fun onSearchLoaded(data:ArrayList<InformasiUmum>)
    fun onSearchFailed()

}
