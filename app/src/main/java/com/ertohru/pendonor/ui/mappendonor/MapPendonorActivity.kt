package com.ertohru.pendonor.ui.mappendonor

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ertohru.pendonor.R
import com.ertohru.pendonor.adapter.PendonorDataAdapter
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.model.Pendonor
import com.ertohru.pendonor.model.PendonorWithDistance
import com.ertohru.pendonor.ui.caripendonor.CariPendonorActivity
import com.ertohru.pendonor.ui.pendonordetail.PendonorDetailActivity
import com.ertohru.pendonor.utils.Loading
import com.ertohru.pendonor.utils.Polyline

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_map_pendonor.*
import kotlinx.android.synthetic.main.dialog_detail_map_pendonor.view.*
import org.json.JSONObject

class MapPendonorActivity : BaseActivity(), MapPendonorView,OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val presenter = MapPendonorPresenter(this,this)
    private val polylines = ArrayList<com.google.android.gms.maps.model.Polyline>()

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
        rvAMP.isNestedScrollingEnabled = false

        btnExpandListAMP.setOnClickListener { bslAMP.expand() }
        bslAMP.setOnProgressListener {
            Log.d("Progress",it.toString())
            if(it == 1f){
                frameRVAMP.visibility = View.VISIBLE
            }else{
                frameRVAMP.visibility = View.GONE
            }
        }

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

        var markerIcon = resources.getDrawable(R.drawable.ic_people, null)
        val b = (markerIcon as BitmapDrawable).bitmap
        val icon = Bitmap.createScaledBitmap(b, 100, 100, false)

        mMap.addMarker(MarkerOptions().position(LatLng(CariPendonorActivity.LAT.toDouble(),CariPendonorActivity.LNG.toDouble())).title("Lokasi Saya").icon(BitmapDescriptorFactory.fromBitmap(icon)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(CariPendonorActivity.LAT.toDouble(),CariPendonorActivity.LNG.toDouble()), 16.0f))

        presenter.addSearchHistory(i.getStringExtra("golongan_darah"))
    }

    @SuppressLint("MissingPermission")
    override fun onPendonorLoaded(data: ArrayList<PendonorWithDistance>?) {

        val markers = ArrayList<Marker>()

        for(i in 0 until data?.size!!){
            var markerIcon = resources.getDrawable(R.drawable.ic_people_red, null)
            val b = (markerIcon as BitmapDrawable).bitmap
            val icon = Bitmap.createScaledBitmap(b, 100, 100, false)

            val marker = mMap.addMarker(MarkerOptions().position(LatLng(data[i].lat.toDouble(), data[i].lng.toDouble())).title(data[i].namaLengkap).icon(BitmapDescriptorFactory.fromBitmap(icon)))
            marker.tag = i.toString()
            markers.add(marker)
        }

        val adapter = PendonorDataAdapter(this, data)
        adapter.notifyDataSetChanged()
        rvAMP.adapter = adapter

        mMap.setOnMarkerClickListener {

            val cb = it

            if (it.tag != null){
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_detail_map_pendonor,null,false)
                dialogView.lbNamaLengkapDDMP.text = data[cb.tag.toString().toInt()].namaLengkap
                dialogView.lbResusDDMP.text = "RESUS: "+data[cb.tag.toString().toInt()].resus
                dialogView.lbJarakDDMP.text = "JARAK: "+data[cb.tag.toString().toInt()].distance.substring(0,4)+" KM"

                val pendonorId = data[it.tag.toString().toInt()].id

                MaterialDialog(this).show {
                    customView(view = dialogView)
                    cornerRadius(6f)
                    positiveButton(text = "LIHAT"){
                        val intent = Intent(applicationContext,PendonorDetailActivity::class.java)
                        intent.putExtra("id",pendonorId)
                        startActivity(intent)
                    }
                    negativeButton(text = "RUTE"){
                        for (polyline in polylines){
                            polyline.remove()
                        }

                        val origin = CariPendonorActivity.LAT + "," + CariPendonorActivity.LNG
                        val destination = data[cb.tag.toString().toInt()].lat + "," + data[cb.tag.toString().toInt()].lng
                        presenter.drawPoly(origin,destination)

                    }
                }
            }

            false
        }

    }

    override fun onPendonorDataEmpty() {
        this.finish()
    }

    override fun onPolyDrawed(poly: PolylineOptions) {
        polylines.add(mMap.addPolyline(poly))
    }


}
