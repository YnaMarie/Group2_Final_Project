package com.cea.auf.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.cea.auf.MainActivity
import com.cea.auf.R
import com.cea.auf.databinding.ActivitySplashScreenBinding
import com.cea.auf.utils.ConstantKeys
import com.cea.auf.utils.TinyDB

class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    lateinit var tinyDB: TinyDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tinyDB= TinyDB(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.bgSplash.startAnimation(animation)

        object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                if (tinyDB.getString(ConstantKeys.USER_ID)==null){
                    val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else{
                    val intent = Intent(this@SplashScreenActivity, MainActivity ::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }
}