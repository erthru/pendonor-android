package com.ertohru.pendonor.ui.mappendonor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ertohru.pendonor.R
import com.ertohru.pendonor.ui.caripendonor.CariPendonorActivity
import com.ertohru.pendonor.ui.pendonordetail.PendonorDetailActivity
import com.ertohru.pendonor.utils.Distance
import kotlinx.android.synthetic.main.list_map_pendonor.view.*
import java.text.DecimalFormat

class PendonorDataAdapter(val context: Context, val data:ArrayList<PendonorData>?) : RecyclerView.Adapter<PendonorDataAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_map_pendonor,parent,false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(context, data!!, position)
    }


    class ViewHolder(val v: View) : RecyclerView.ViewHolder(v){
        fun init(context: Context, data: ArrayList<PendonorData>, position: Int){
            v.lbNameLMP.text = data[position].namaLengkap
            v.lbResusLMP.text = "RESUS: "+data[position].resus
            v.lbListLMP.text = DecimalFormat("#.##").format(Distance.calculate(
                CariPendonorActivity.LAT.toDouble(),
                CariPendonorActivity.LNG.toDouble(),
                data[position].lat.toDouble(),
                data[position].lng.toDouble()
            )).toString()+ " KM"

            v.cardLMP.setOnClickListener {
                val intent = Intent(context,PendonorDetailActivity::class.java)
                intent.putExtra("id",data[position].id)
                context.startActivity(intent)
            }
        }
    }
}