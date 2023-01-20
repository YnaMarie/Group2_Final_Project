package com.cea.auf.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cea.auf.adapter.JobAdapter
import com.cea.auf.adapter.SaveJobAdapter
import com.cea.auf.databinding.ActivitySaveJobBinding
import com.cea.auf.model.JOBSModel
import com.cea.auf.model.SaveJobModel
import com.cea.auf.utils.JobDatabase
import java.util.*
import kotlin.collections.ArrayList

class SaveJobActivity : AppCompatActivity() {
    lateinit var binding: ActivitySaveJobBinding
    lateinit var database: JobDatabase

    val data = ArrayList<SaveJobModel>()
    lateinit var adapter: SaveJobAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = JobDatabase.getDatabase(this)

        recyclerView()

    }

    private fun recyclerView() {
        data.clear()
        database.contactDao().getSaveJob().observe(this) {
            Log.d("wow123", "recyclerView: " + it.toString())
            if (it == null) {
                Toast.makeText(this, "No save job", Toast.LENGTH_SHORT).show()
            } else {
                data.addAll(it)
                binding.searchEdt.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                    }

                    override fun afterTextChanged(s: Editable) {
                        filteration(s.toString(), it)
                    }
                })
            }

            binding.recyclerView.layoutManager = LinearLayoutManager(this)


            adapter = SaveJobAdapter(data, this, database)

            binding.recyclerView.adapter = adapter
        }
    }
    private fun filteration(toString: String, data: List<SaveJobModel>) {

        val filteredList: java.util.ArrayList<SaveJobModel> = java.util.ArrayList();

        for (item:SaveJobModel in data) {
            if (item.skill.toLowerCase().contains(toString.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterdList(filteredList)
    }

}
