package com.ertohru.pendonor.ui.profil


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide

import com.ertohru.pendonor.R
import com.ertohru.pendonor.base.BaseFragment
import com.ertohru.pendonor.utils.ApiEndPoint
import com.ertohru.pendonor.utils.MyGlideEngine
import com.ertohru.pendonor.utils.SharedPrefPengguna
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.dialog_edit_profil.view.*
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.android.synthetic.main.fragment_profil.view.*
import java.io.File

class ProfilFragment : BaseFragment(), ProfilView {

    private val presenter = ProfilPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profil, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Profil"
        setHasOptionsMenu(true)

        initProgressBar(v.pbProfil)

        presenter.loadProfil(SharedPrefPengguna(context!!).id()!!)

        v.btnSimpanPasswordProfil.setOnClickListener {
            if(v.txPasswordProfil.text.toString().isNullOrEmpty() || v.txPasswordReProfil.text.toString().isNullOrEmpty()){
                toastError("Lengkapi data")
            }else if(v.txPasswordProfil.text.toString() != v.txPasswordReProfil.text.toString()){
                toastError("Password tidak cocok")
            }else{
                presenter.gantiPassword(SharedPrefPengguna(context!!).id()!!,v.txPasswordProfil.text.toString())
            }
        }

        v.btnSelectImageProfil.setOnClickListener {
            Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(1)
                .imageEngine(MyGlideEngine())
                .forResult(1)
        }

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_profil,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId){
            R.id.itemEditMP -> showEditDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showEditDialog(){

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_profil,null,false)
        dialogView.txNamaLengkapDEP.setText(lbNamaLengkapProfil.text.toString())
        dialogView.txEmailDEP.setText(lbEmailProfil.text.toString())

        MaterialDialog(context!!).show {
            customView(view = dialogView)
            cornerRadius(6f)
            positiveButton(text = "PERBARUI"){
                if(dialogView.txNamaLengkapDEP.text.toString().isNullOrEmpty() || dialogView.txEmailDEP.text.toString().isNullOrEmpty()){
                    toastError("Lengkapi data")
                }else{
                    presenter.updateProfil(SharedPrefPengguna(context!!).id()!!,dialogView.txNamaLengkapDEP.text.toString(),dialogView.txEmailDEP.text.toString())
                }
            }
            negativeButton(text = "BATAL")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK){
            Log.d("MATISSE",Matisse.obtainResult(data).toString())
            if(Matisse.obtainResult(data).size != 0){
                val pickedImgFile = File(Matisse.obtainPathResult(data)[0])
                presenter.gantiFoto(SharedPrefPengguna(context!!).id()!!,pickedImgFile)
            }
        }

    }

    override fun onProfilLoaded(data: HashMap<String, String>) {
        Glide.with(context!!).load(ApiEndPoint.PENDONOR_UPLOADS+data["foto"]).into(v.imgFotoProfil)
        v.lbNamaLengkapProfil.text = data["nama_lengkap"]
        v.lbEmailProfil.text = data["email"]
        v.layoutProfil.visibility = View.VISIBLE
    }

    override fun onProfilFailed() {
        presenter.loadProfil(SharedPrefPengguna(context!!).id()!!)
    }

    override fun onPasswordChanged() {
        v.txPasswordProfil.setText("")
        v.txPasswordProfil.clearFocus()
        v.txPasswordReProfil.setText("")
        v.txPasswordReProfil.clearFocus()
    }

    override fun onProfilUpdated() {
        presenter.loadProfil(SharedPrefPengguna(context!!).id()!!)
    }

    override fun onFotoUpdated() {
        presenter.loadProfil(SharedPrefPengguna(context!!).id()!!)
    }


}
