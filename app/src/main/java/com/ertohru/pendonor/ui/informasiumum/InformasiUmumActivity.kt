package com.ertohru.pendonor.ui.informasiumum

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ertohru.pendonor.R
import com.ertohru.pendonor.adapter.InformasiUmumAdapter
import com.ertohru.pendonor.base.BaseActivity
import com.ertohru.pendonor.model.InformasiUmum
import com.ertohru.pendonor.ui.informasiumumsearch.InformasiUmumSearchActivity
import kotlinx.android.synthetic.main.activity_informasi_umum.*

class InformasiUmumActivity : BaseActivity(),InformasiUmumView {

    private val presenter = InformasiUmumPresenter(this)
    private lateinit var parcelable: Parcelable
    private var datas = ArrayList<InformasiUmum>()

    companion object{
        var ON_LAST_PAGE = false
        var CURRENT_PAGE = 1
        var LAST_PAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi_umum)

        supportActionBar?.title = "Informasi Umum"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvIU.setHasFixedSize(true)
        rvIU.layoutManager = LinearLayoutManager(this)
        initProgressBar(pbIU)
        dismissProgressBottom()

        rvIU.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            var directiorDown:Boolean = false

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                directiorDown = dy > 0
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (recyclerView?.canScrollVertically(1)?.not()!!
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && directiorDown) {

                    if(!ON_LAST_PAGE){
                        presenter.loadInformasiUmumNext()
                        parcelable = rvIU.layoutManager?.onSaveInstanceState()!!
                    }

                }else{

                }

            }
        })

        swipeIU.setColorSchemeResources(R.color.colorAccent)
        swipeIU.setOnRefreshListener { this.recreate() }

    }

    override fun onResume() {
        super.onResume()
        rvIU.adapter = null
        presenter.loadInformasiUmum()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_informasi_umum,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> this.finish()
            R.id.itemCariMIU -> startActivity(Intent(this,InformasiUmumSearchActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onInformasiUmumLoaded(data: ArrayList<InformasiUmum>?) {
        datas.addAll(data!!)
        val adapter = InformasiUmumAdapter(this, datas)
        adapter.notifyDataSetChanged()
        rvIU.adapter = adapter
    }

    override fun onInformasiUmumNextLoaded(data: ArrayList<InformasiUmum>?) {
        datas.addAll(data!!)
        val adapter = InformasiUmumAdapter(this, datas)
        adapter.notifyDataSetChanged();
        rvIU.adapter = adapter
        rvIU.layoutManager?.onRestoreInstanceState(parcelable)
    }

    override fun showProgressBottom() {
        pbBottomIU.visibility = View.VISIBLE
    }

    override fun dismissProgressBottom() {
        pbBottomIU.visibility = View.GONE
    }

}
