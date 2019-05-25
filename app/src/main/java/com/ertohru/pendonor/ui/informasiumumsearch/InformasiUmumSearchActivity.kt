package com.ertohru.pendonor.ui.informasiumumsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ertohru.pendonor.R
import com.ertohru.pendonor.adapter.InformasiUmumAdapter
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.model.InformasiUmum
import kotlinx.android.synthetic.main.activity_informasi_umum_search.*

class InformasiUmumSearchActivity : BaseActivity(),InformasiUmumSearchView {

    private val presenter = InformasiUmumSearchPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi_umum_search)
        supportActionBar?.hide()

        btnBackAIUS.setOnClickListener { this.finish() }

        initProgressBar(pbAIUS)

        rvAIUS.setHasFixedSize(true)
        rvAIUS.layoutManager = LinearLayoutManager(this)

        btnOKAIUS.setOnClickListener { presenter.loadSearch(txSearchAIUS.text.toString()) }

    }

    override fun onSearchLoaded(data: ArrayList<InformasiUmum>) {
        val adapter = InformasiUmumAdapter(this,data)
        adapter.notifyDataSetChanged()
        rvAIUS.adapter = adapter
    }

    override fun onSearchFailed() {
        this.finish()
    }

}
