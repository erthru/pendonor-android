package com.ertohru.pendonor.ui.pendonordetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pendonor_detail.*

class PendonorDetailActivity : BaseActivity(),PendonorDetailView {

    val presenter = PendonorDetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendonor_detail)

        supportActionBar?.title = "Pendonor Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnTelpAPD.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+lbTelpAPD.text.toString()))
            startActivity(i)
        }

        btnPesanAPD.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("smsto:" + Uri.encode(lbTelpAPD.text.toString()))
            i.putExtra("address",lbTelpAPD.text.toString())
            startActivity(i)
        }

    }

    override fun onResume() {
        super.onResume()
        val i = intent
        presenter.loadPendonorDetail(i.getStringExtra("id"))
    }

    override fun dataPendonorLoaded(data: HashMap<String, String>) {
        lbNamaLengkapAPD.text = data["nama_lengkap"]
        lbAlamatAPD.text = data["alamat"]
        lbProvinsiAPD.text = data["provinsi"]
        lbKotakabuAPD.text = data["kota_kabupaten"]
        lbTelpAPD.text = data["telp"]
        lbJenkelAPD.text = data["jenkel"]
        lbGolonganDarahAPD.text = data["golongan_darah"]
        lbResusAPD.text = data["resus"]
        layoutAPD.visibility = View.VISIBLE
    }

    override fun onDataPendonorFailed() {
        this.finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> this.finish()
        }

        return super.onOptionsItemSelected(item)
    }

}
