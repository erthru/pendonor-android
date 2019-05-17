package com.ertohru.pendonor.utils.spinneritem

import android.R
import android.content.Context
import android.widget.ArrayAdapter

class GolonganDarahSpinner(val context: Context){

    fun list() : ArrayAdapter<String>{

        val golonganDarah = arrayOf("A","B","AB","O")

        return ArrayAdapter(context, R.layout.simple_dropdown_item_1line, golonganDarah)

    }

}