package com.ertohru.pendonor.ui.register

import android.content.Intent
import android.os.Bundle
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.ui.login.LoginActivity
import com.ertohru.pendonor.utils.spinneritem.GolonganDarahSpinner
import com.ertohru.pendonor.utils.spinneritem.ResusSpinner
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), RegisterView {

    val presenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        btnLoginRegister.setOnClickListener { startActivity(Intent(this,LoginActivity::class.java)) }

        btnRegister.setOnClickListener {
            presenter.register(
                    txNamaLengkapRegister.text.toString(),
                    txEmailRegistrasi.text.toString(),
                    txPasswordRegister.text.toString(),
                    txPasswordReRegister.text.toString()
            )
        }

    }

    override fun onRegisterSuccess() {
        startActivity(Intent(this,LoginActivity::class.java))
    }
}
