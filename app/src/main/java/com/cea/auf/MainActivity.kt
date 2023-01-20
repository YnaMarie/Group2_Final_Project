package com.cea.auf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cea.auf.base.JobsActivity
import com.cea.auf.base.LoginActivity
import com.cea.auf.base.SaveJobActivity
import com.cea.auf.databinding.ActivityMainBinding
import com.cea.auf.utils.ConstantKeys
import com.cea.auf.utils.TinyDB

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var tinyDB: TinyDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tinyDB= TinyDB(this)
        clicks()

    }

    override fun onBackPressed() {
        finishAffinity()
    }
    private fun clicks() {
        binding.loutImg.setOnClickListener {
            tinyDB.putString(ConstantKeys.USER_ID,"")
            val intent=Intent(this,LoginActivity::class.java)
            intent.action = "com.package.ACTION_LOGOUT"
            startActivity(intent)

            finish()

        }
        binding.searchJobBtn.setOnClickListener {
            var intent=Intent(this,JobsActivity::class.java)
            startActivity(intent)
        }
        binding.savedJobBtn.setOnClickListener {
            var intent=Intent(this,SaveJobActivity::class.java)
            startActivity(intent)
        }
    }
}