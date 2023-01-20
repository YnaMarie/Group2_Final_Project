package com.cea.auf.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cea.auf.databinding.ActivitySignUpBinding
import com.cea.auf.model.RegistrationModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clicks()
//        database=FirebaseDatabase.getInstance().getReference("users")
        val database1: FirebaseDatabase = Firebase.database
       database = database1.getReference("Users")



    }



    private fun clicks() {
        binding.signupBtn.setOnClickListener {
            if (binding.passwordEdt.text.toString() == ""){
                binding.passwordEdt.requestFocus()
                binding.passwordEdt.error = "Please Enter Details"
                return@setOnClickListener
            }
            if (binding.userNameEdt.text.toString() == ""){
                binding.userNameEdt.requestFocus()
                binding.userNameEdt.error = "Please Enter Details"
                return@setOnClickListener
            }
            val userName:String=binding.userNameEdt.text.toString()
            val password:String=binding.passwordEdt.text.toString()
            val registration=RegistrationModel(userName,password)
            database.child(database.push().key.toString()).setValue(registration).addOnSuccessListener {
                binding.userNameEdt.text=null
                binding.passwordEdt.text=null
                Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show()
            }



        }
        binding.signInTxt.setOnClickListener {
            onBackPressed()
        }
    }
}