package com.cea.auf.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cea.auf.databinding.ActivityApplyNowBinding
import com.cea.auf.utils.ConstantKeys
import com.cea.auf.utils.TinyDB
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ApplyNowActivity : AppCompatActivity() {
    lateinit var binding:ActivityApplyNowBinding
    lateinit var database: DatabaseReference
    lateinit var tinyDB: TinyDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityApplyNowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tinyDB= TinyDB(this)
        click()

        val database1: FirebaseDatabase = Firebase.database
        database = database1.getReference("Users")

    }

    private fun click() {
        binding.applyBtn.setOnClickListener {
            if (binding.addressEdt.text.toString() == ""){
                binding.addressEdt.requestFocus()
                binding.addressEdt.error = "Please Enter Details"
                return@setOnClickListener

            }
            if (binding.fullNameEdt.text.toString() == ""){
                binding.fullNameEdt.requestFocus()
                binding.fullNameEdt.error = "Please Enter Details"
                return@setOnClickListener

            }
            if (binding.emailEdt.text.toString() == ""){
                binding.emailEdt.requestFocus()
                binding.emailEdt.error = "Please Enter Details"
                return@setOnClickListener

            }
            if (binding.phoneEdt.text.toString() == ""){
                binding.phoneEdt.requestFocus()
                binding.phoneEdt.error = "Please Enter Details"
                return@setOnClickListener
            }
            val fullName:String=binding.fullNameEdt.text.toString()
            val email:String=binding.emailEdt.text.toString()
            val number:String=binding.phoneEdt.text.toString()
            val address:String=binding.addressEdt.text.toString()
            val mapWithValues = mapOf("fullName" to fullName,
                "email" to email,
                "number" to number,
                "address" to address,
                "user_name" to tinyDB.getString(ConstantKeys.USER_NAME),
                "password" to tinyDB.getString(ConstantKeys.PASSWORD)
            )
            database.child(tinyDB.getString(ConstantKeys.USER_ID)).setValue(mapWithValues).addOnSuccessListener {
                binding.fullNameEdt.text=null
                binding.addressEdt.text=null
                binding.emailEdt.text=null
                binding.phoneEdt.text=null
                Toast.makeText(this, "Successfully Apply", Toast.LENGTH_SHORT).show()
            }
        }
    }


}