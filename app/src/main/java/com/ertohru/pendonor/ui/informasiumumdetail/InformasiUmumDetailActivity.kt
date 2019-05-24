package com.ertohru.pendonor.ui.informasiumumdetail

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.Const.Companion.KONEKSI_ERROR
import kotlinx.android.synthetic.main.activity_informasi_umum_detail.*
import org.jsoup.Jsoup

class InformasiUmumDetailActivity : BaseActivity(),InformasiUmumDetailView {

    val presenter = InformasiUmumDetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi_umum_detail)

        supportActionBar?.title = "Informasi Umum Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initProgressBar(pbAIUD)

        val i = intent
        val id = i.getStringExtra("id")

        presenter.loadDetail(id)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetailLoaded(data: HashMap<String, String>) {
        Glide.with(this).load(ApiEndPoint.PENDONOR_UPLOADS+data["thumbnail"]).into(imgAIUD)
        lbJudulAIUD.text = data["judul"]
        lbUpdatedAtAIUD.text = "Diperbarui pada: "+data["updated_at"]
        lbAdminAIUD.text = "Penulis: "+data["penulis"]
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            lbKontenAIUD.text = Html.fromHtml(data["konten"],Html.FROM_HTML_MODE_LEGACY)
        } else {
            lbKontenAIUD.text = Html.fromHtml(data["konten"])
        }
    }

    override fun onDetailFailed() {
        this.finish()
    }

}
