package com.ertohru.pendonor.ui.informasiumum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ertohru.pendonor.R
import com.ertohru.pendonor.utils.ApiEndPoint
import kotlinx.android.synthetic.main.list_informasi_umum.view.*

class InformasiUmumAdapter(private val context: Context,private val data:ArrayList<InformasiUmumData>?) : RecyclerView.Adapter<InformasiUmumAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_informasi_umum,parent,false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.init(context,data!!,position)
    }


    class Holder(val v: View) : RecyclerView.ViewHolder(v){
        fun init(context: Context,data: ArrayList<InformasiUmumData>, position: Int){
            v.lbJudulLIU.text = data[position].judul
            v.lbKontenLIU.text = data[position].rendered_konten
            Glide.with(context).load(ApiEndPoint.PENDONOR_UPLOADS+data[position].thumbnail).into(v.imgLIU)
            v.cardLIU.setOnClickListener {

            }
        }
    }
}