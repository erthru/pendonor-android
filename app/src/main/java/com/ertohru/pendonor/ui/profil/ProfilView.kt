package com.ertohru.pendonor.ui.profil

import com.ertohru.pendonor.base.BaseView

interface ProfilView: BaseView {

    fun onProfilLoaded(data:HashMap<String,String>)
    fun onProfilFailed()
    fun onPasswordChanged()
    fun onProfilUpdated()
    fun onFotoUpdated()

}