package com.ertohru.pendonor.ui.splash

import android.Manifest
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.afollestad.materialdialogs.MaterialDialog
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.ui.login.LoginActivity
import com.ertohru.pendonor.ui.main.MainActivity
import com.ertohru.pendonor.utils.SharedPrefPengguna
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {

    var delay:Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initProgressBar(pbSplash)

        if(!EasyPermissions.hasPermissions(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) || !EasyPermissions.hasPermissions(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)){
            EasyPermissions.requestPermissions(this@SplashActivity,"Aplikasi membutuhkan akses lokasi",992, Manifest.permission.ACCESS_FINE_LOCATION)
            EasyPermissions.requestPermissions(this@SplashActivity,"Aplikasi membutuhkan akses lokasi",991, Manifest.permission.ACCESS_COARSE_LOCATION)
        }else{
            Handler().postDelayed({
                if(!SharedPrefPengguna(this).isLogin()!!)
                    startActivity(Intent(this,LoginActivity::class.java))
                else
                    startActivity(Intent(this,MainActivity::class.java))
            },delay)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        MaterialDialog(this).show {
            title(text = "Peringatan")
            message(text = "Sayangnya aplikasi ini membutuhkan akses tersebut.")
            cornerRadius(6f)
            positiveButton(text = "TUTUP"){
                finish()
            }
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

        Handler().postDelayed({
            if(!SharedPrefPengguna(this).isLogin()!!)
                startActivity(Intent(this,LoginActivity::class.java))
            else
                startActivity(Intent(this,MainActivity::class.java))
        },delay)

    }

}
