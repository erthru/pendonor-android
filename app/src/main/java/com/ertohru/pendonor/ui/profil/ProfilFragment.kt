package com.ertohru.pendonor.ui.profil


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseFragment

class ProfilFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profil, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Profil"

        return v
    }


}
