package com.cea.auf.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cea.auf.MainActivity
import com.cea.auf.databinding.ActivityLoginBinding
import com.cea.auf.utils.ConstantKeys
import com.cea.auf.utils.TinyDB
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
     lateinit var binding: ActivityLoginBinding
    lateinit var database: DatabaseReference
    lateinit var tinyDB: TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tinyDB= TinyDB(this)
        clicks()

        val database1: FirebaseDatabase = Firebase.database
        database = database1.getReference("Users")

    }

    private fun clicks() {
        binding.signinBtn.setOnClickListener {
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

            database.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val username=it.child("user_name").getValue(String::class.java)
                        val password=it.child("password").getValue(String::class.java)
                        if(username.equals(binding.userNameEdt.text.toString())&&
                                password.equals(binding.passwordEdt.text.toString())){
                            tinyDB.putString(ConstantKeys.USER_ID,it.key)
                            tinyDB.putString(ConstantKeys.USER_NAME,username)
                            tinyDB.putString(ConstantKeys.PASSWORD,password)

                            startActivity(Intent(this@LoginActivity,MainActivity::class.java))


                        }
                    }
                    if (tinyDB.getString(ConstantKeys.USER_ID)==""){
                        Toast.makeText(this@LoginActivity, "incorrect information", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@LoginActivity, ""+error.message, Toast.LENGTH_SHORT).show()
                }

            })

        }
        binding.signUpTxt.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }


    }
}