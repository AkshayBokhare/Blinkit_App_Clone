package com.nts.userblinkitcone.utils

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nts.userblinkitcone.databinding.ProgressDialogBinding

object Utils {
                private var dialog:AlertDialog?=null

    fun showToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
    fun showDialog(context: Context,message: String){
        val progress = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        progress.tvMessage.text = message
        dialog = AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog!!.show()
    }

    fun hideDialog(){
        dialog?.dismiss()
    }

    private var firebaseAuthInstance:FirebaseAuth?=null
    fun getAuthInstance():FirebaseAuth{
        if (firebaseAuthInstance==null){
            firebaseAuthInstance=FirebaseAuth.getInstance()
        }
        return firebaseAuthInstance!!
    }

    fun getCurrentUserId() : String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

}