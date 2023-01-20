package com.cea.auf.base

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cea.auf.databinding.ActivityMoreDetailBinding
import com.cea.auf.model.SaveJobModel
import com.cea.auf.utils.JobDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MoreDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreDetailBinding
    lateinit var database: JobDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = JobDatabase.getDatabase(this)
        val skill: String = intent.getStringExtra("SKILL").toString()
        val id: String = intent.getStringExtra("ID").toString()
        val description: String = intent.getStringExtra("DESCRIPTION").toString()
        val title: String = intent.getStringExtra("TITLE").toString()

        binding.descriptionTxt.text = description
        binding.jobTitleTxt.text = title
        binding.skillsTxt.text = skill
        binding.descriptionTxt.movementMethod = ScrollingMovementMethod()
        binding.applyNowBtn.setOnClickListener {
            startActivity(Intent(this, ApplyNowActivity::class.java))
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.saveJobImg.setOnClickListener {
            GlobalScope.launch {
                database.contactDao().insertSaveJob(SaveJobModel(id, true,
                    description,
                    title,
                    skill))

            }
            Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}