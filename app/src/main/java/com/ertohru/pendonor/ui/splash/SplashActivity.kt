package com.ertohru.pendonor.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.ui.login.LoginActivity
import com.ertohru.pendonor.ui.main.MainActivity
import com.ertohru.pendonor.utils.SharedPrefPengguna
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    var delay:Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initProgressBar(pbSplash)

        Handler().postDelayed({
            if(!SharedPrefPengguna(this).isLogin()!!)
                //intentTo(LoginActivity::class.java)
                startActivity(Intent(this,LoginActivity::class.java))
            else
                startActivity(Intent(this,MainActivity::class.java))
        },delay)

    }

    private fun listenerHandling(){

    }
}
