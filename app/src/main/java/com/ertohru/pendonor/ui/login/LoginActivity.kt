package com.ertohru.pendonor.ui.login

import android.content.Intent
import android.os.Bundle
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.ui.main.MainActivity
import com.ertohru.pendonor.ui.register.RegisterActivity
import com.ertohru.pendonor.utils.SharedPrefPengguna
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginView {

    private val presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        btnRegistrasiLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener { presenter.auth(txEmailLogin.text.toString(),txPasswordLogin.text.toString()) }

    }

    override fun onLoginSuccess(id: String) {

        SharedPrefPengguna(this).set(id)
        startActivity(Intent(this,MainActivity::class.java))

    }
}
