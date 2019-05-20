package com.ertohru.pendonor.ui.informasiumum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import kotlinx.android.synthetic.main.activity_informasi_umum.*

class InformasiUmumActivity : BaseActivity(),InformasiUmumView {

    private val presenter = InformasiUmumPresenter(this)

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

    }

    override fun onResume() {
        super.onResume()
        rvIU.adapter = null
        presenter.loadInformasiUmum()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onInformasiUmumLoaded(data: ArrayList<InformasiUmumData>?) {
        val adapter = InformasiUmumAdapter(this,data)
        adapter.notifyDataSetChanged()
        rvIU.adapter = adapter
    }

    override fun onInformasiUmumNextLoaded(data: ArrayList<InformasiUmumData>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressBottom() {
        pbBottomIU.visibility = View.VISIBLE
    }

    override fun dismissProgressBottom() {
        pbBottomIU.visibility = View.GONE
    }

}
