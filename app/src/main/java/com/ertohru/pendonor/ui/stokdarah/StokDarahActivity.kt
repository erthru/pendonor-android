package com.ertohru.pendonor.ui.stokdarah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseActivity
import kotlinx.android.synthetic.main.activity_stok_darah.*

class StokDarahActivity : BaseActivity(),StokDarahView {

    private val presenter = StokDarahPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stok_darah)

        supportActionBar?.title = "Stok Darah"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onResume() {
        super.onResume()
        presenter.loadStokDarah()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStokDarahLoaded(data: HashMap<String, String>) {
        lbTotalPendonorASD.text = data["pendonor_total"]+" Pendonor"
        lbBisaDonorASD.text = data["bisa_donor_total"]+" Pendonor"
        lbBisaDonorAASD.text = data["bisa_donor_a_total"]+" Pendonor"
        lbBisaDonorBASD.text = data["bisa_donor_b_total"]+" Pendonor"
        lbBisaDonorABASD.text = data["bisa_donor_ab_total"]+" Pendonor"
        lbBisaDonorOASD.text = data["bisa_donor_o_total"]+" Pendonor"
    }

    override fun onStokDarahFailedToLoad() {
        this.finish()
    }
}
