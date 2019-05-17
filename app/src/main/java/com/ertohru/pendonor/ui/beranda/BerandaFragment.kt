package com.ertohru.pendonor.ui.beranda


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseFragment
import com.ertohru.pendonor.ui.caripendonor.CariPendonorActivity
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*

class BerandaFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_beranda, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Beranda"

        v.btnCariPendonorBeranda.setOnClickListener { startActivity(Intent(context,CariPendonorActivity::class.java)) }

        return v
    }


}
