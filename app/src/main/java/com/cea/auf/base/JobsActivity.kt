package com.cea.auf.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cea.auf.adapter.JobAdapter
import com.cea.auf.databinding.ActivityJobsBinding
import com.cea.auf.model.JOBSModel
import com.cea.auf.utils.JobDatabase
import com.cea.auf.utils.RetrofitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class JobsActivity : AppCompatActivity() {
    lateinit var database: JobDatabase
    val data = ArrayList<JOBSModel.Result>()
    lateinit var   adapter: JobAdapter

    lateinit var binding: ActivityJobsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database=JobDatabase.getDatabase(this)
        recyclerView()
        apiCall()
        getData()











    }
    @SuppressLint("NotifyDataSetChanged")
    fun getData() {
        data.clear()
        database.contactDao().getJob().observe(this) { it ->
            if (it==null){
                Toast.makeText(this,"No data to show offline Connect to internet", Toast.LENGTH_SHORT).show()
            }else {
                data.addAll(it.results)
                adapter.notifyDataSetChanged()
                binding.searchEdt.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable) {
                        filteration(s.toString(),it.results)
                    }
                })
            }
        }
    }


    private fun apiCall() {
        val getUser: Call<JOBSModel?>? =
            RetrofitClient.getInstance().api.getJobs("0")
        getUser!!.enqueue(object : Callback<JOBSModel?> {


            override fun onResponse(call: Call<JOBSModel?>, response: Response<JOBSModel?>) {
                GlobalScope.launch {
                    database.contactDao().insertJob(JOBSModel(
                        response.body()!!.page,
                        response.body()!!.results,
                    ))
                    Log.d("wow123", "onResponse: "+response.body())


                }
            }

            override fun onFailure(call: Call<JOBSModel?>, t: Throwable) {
                Log.d("wow123", "onFailure: "+t.message)
                Toast.makeText(this@JobsActivity,""+t.message, Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun filteration(toString: String, data: List<JOBSModel.Result>) {

        val filteredList: ArrayList<JOBSModel.Result> = ArrayList();

        for (item:JOBSModel.Result in data) {
            if (item.categories[0].name.toLowerCase().contains(toString.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterdList(filteredList)
    }

    private fun recyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        adapter = JobAdapter(data,this,database)

        // Setting the Adapter with the recyclerview
        binding.recyclerView.adapter = adapter
    }



}