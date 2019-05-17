package com.ertohru.pendonor.ui.login

import com.ertohru.pendonor.base.BaseView

interface LoginView: BaseView {
    fun onLoginSuccess(id:String)
}
