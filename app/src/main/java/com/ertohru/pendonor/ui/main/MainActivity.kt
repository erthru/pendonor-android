package com.ertohru.pendonor.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.ui.profil.ProfilFragment
import com.ertohru.pendonor.ui.beranda.BerandaFragment
import com.ertohru.pendonor.ui.login.LoginActivity
import com.ertohru.pendonor.utils.SharedPrefPengguna
import kotlinx.android.synthetic.main.nav_header_main.view.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    private val presenter = MainPresenter(this)
    private lateinit var headerView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        headerView = navView.getHeaderView(0)

        supportFragmentManager.beginTransaction().replace(R.id.flMain,
            BerandaFragment()
        ).commit()

        presenter.loadDataUser(SharedPrefPengguna(this).id()!!)

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.flMain,
                    BerandaFragment()
                ).commit()
            }
            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.flMain,
                    ProfilFragment()
                ).commit()
            }
            R.id.nav_logout -> {
                SharedPrefPengguna(this).destroy()
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onUserDataLoaded(data: HashMap<String, String>) {
        headerView.txNamaLengkapHeaderMain.text = data["nama_lengkap"]
        headerView.txEmailHeaderMain.text = data["email"]
        requirePermission()
    }

    override fun onUserDataFailed() {
        finishAffinity()
    }

    private fun requirePermission(){
        if(!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
            EasyPermissions.requestPermissions(this,"Aplikasi membutuhkan akses lokasi",991,Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            EasyPermissions.requestPermissions(this,"Aplikasi membutuhkan akses lokasi",992,Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

}
