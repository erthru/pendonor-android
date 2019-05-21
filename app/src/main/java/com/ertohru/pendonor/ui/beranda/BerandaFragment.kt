package com.ertohru.pendonor.ui.beranda


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseFragment
import com.ertohru.pendonor.ui.caripendonor.CariPendonorActivity
import com.ertohru.pendonor.ui.informasiumum.InformasiUmumActivity
import com.ertohru.pendonor.ui.stokdarah.StokDarahActivity
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*

class BerandaFragment : BaseFragment(),BerandaView {

    private val presenter = BerandaPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_beranda, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Beranda"

        v.rvInformasiUmumBeranda.setHasFixedSize(true)
        v.rvInformasiUmumBeranda.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        v.rvInformasiUmumBeranda.isNestedScrollingEnabled = false

        initProgressBar(v.pbBeranda)

        v.btnCariPendonorBeranda.setOnClickListener { startActivity(Intent(context,CariPendonorActivity::class.java)) }
        v.btnStokDarahBeranda.setOnClickListener { startActivity(Intent(context,StokDarahActivity::class.java)) }
        v.btnInformasiUmumBeranda.setOnClickListener { startActivity(Intent(context,InformasiUmumActivity::class.java)) }

        v.swipeBeranda.setColorSchemeResources(R.color.colorAccent)
        v.swipeBeranda.setOnRefreshListener {
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }

        return v
    }

    override fun onResume() {
        super.onResume()
        presenter.loadInformasiUmum()
    }

    override fun onInformasiUmumLoaded(data: ArrayList<InformasiUmumData>) {
        val adapter = InformasiUmumAdapter(context!!,data)
        adapter.notifyDataSetChanged()
        v.rvInformasiUmumBeranda.adapter = adapter
    }


}
