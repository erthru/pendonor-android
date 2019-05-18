package com.ertohru.pendonor.ui.mappendonor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.ui.caripendonor.CariPendonorActivity
import com.ertohru.pendonor.ui.pendonordetail.PendonorDetailActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map_pendonor.*
import kotlinx.android.synthetic.main.dialog_detail_map_pendonor.view.*

class MapPendonorActivity : BaseActivity(), MapPendonorView,OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val presenter = MapPendonorPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_pendonor)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapAMP) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportActionBar?.title = "Map Pendonor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvAMP.setHasFixedSize(true)
        rvAMP.layoutManager = LinearLayoutManager(this)

        btnExpandListAMP.setOnClickListener { bslAMP.expand() }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        val i = intent
        presenter.loadPendonor(
            i.getStringExtra("golongan_darah"),
            CariPendonorActivity.LAT,
            CariPendonorActivity.LNG
        )
        mMap = googleMap
    }

    @SuppressLint("MissingPermission")
    override fun onPendonorLoaded(data: ArrayList<PendonorData>?) {

        val markers = ArrayList<Marker>()

        for(i in 0 until data?.size!!){
            val marker = mMap.addMarker(MarkerOptions().position(LatLng(data[i].lat.toDouble(), data[i].lng.toDouble())).title(data[i].namaLengkap))
            marker.tag = i.toString()
            markers.add(marker)
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(CariPendonorActivity.LAT.toDouble(),CariPendonorActivity.LNG.toDouble()), 16.0f))

        val adapter = PendonorDataAdapter(this,data)
        adapter.notifyDataSetChanged()
        rvAMP.adapter = adapter

        mMap.setOnMarkerClickListener {

            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_detail_map_pendonor,null,false)
            dialogView.lbNamaLengkapDDMP.text = data[it.tag.toString().toInt()].namaLengkap
            dialogView.lbResusDDMP.text = "RESUS: "+data[it.tag.toString().toInt()].resus

            val pendonorId = data[it.tag.toString().toInt()].id

            MaterialDialog(this).show {
                customView(view = dialogView)
                cornerRadius(6f)
                positiveButton(text = "LIHAT"){
                    val intent = Intent(applicationContext,PendonorDetailActivity::class.java)
                    intent.putExtra("id",pendonorId)
                    startActivity(intent)
                }
                negativeButton(text = "BATAL")
            }

            false
        }

    }

    override fun onPendonorDataEmpty() {
        this.finish()
    }

}
