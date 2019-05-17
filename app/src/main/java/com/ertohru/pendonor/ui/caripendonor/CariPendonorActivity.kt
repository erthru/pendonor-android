package com.ertohru.pendonor.ui.caripendonor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.ui.mappendonor.MapPendonorActivity
import com.ertohru.pendonor.utils.SharedPrefPengguna
import com.ertohru.pendonor.utils.spinneritem.GolonganDarahSpinner
import com.vanillaplacepicker.data.VanillaAddress
import com.vanillaplacepicker.presentation.builder.VanillaPlacePicker
import com.vanillaplacepicker.utils.KeyUtils
import kotlinx.android.synthetic.main.activity_cari_pendonor.*

class CariPendonorActivity : BaseActivity(),CariPendonorView {

    private val presenter = CariPendonorPresenter(this,this)

    companion object {
        var LAT = ""
        var LNG = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_pendonor)

        supportActionBar?.title = "Cari Pendonor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        spinnerGolonganDarahACP.setAdapter(GolonganDarahSpinner(this).list())

        presenter.loadUserCurrentLocation()

        btnGantiACP.setOnClickListener {
            placePicker()
        }

        btnACP.setOnClickListener {
            if (LAT == "" || LNG == ""){
                toastError("Lokasi belum ditentukan.")
            }else{
                startActivity(Intent(this, MapPendonorActivity::class.java))
            }
        }

    }

    override fun onUserCurrentLocationLoaded(data: HashMap<String, String>) {
        lbLokasiACP.text = data["location_name"]
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> this.finish()
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("MissingPermission")
    private fun placePicker(){

        val lm: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        val intent = VanillaPlacePicker.Builder(this)
            .withLocation(location.latitude, location.longitude)
            .enableMap()
            .setMapPinDrawable(android.R.drawable.ic_menu_mylocation)
            .build()

        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            val vanillaAddress = data?.getSerializableExtra(KeyUtils.SELECTED_PLACE) as VanillaAddress
            lbLokasiACP.text = vanillaAddress.formattedAddress
            LAT = vanillaAddress.latitude.toString()
            LNG = vanillaAddress.longitude.toString()
        }
    }

}
