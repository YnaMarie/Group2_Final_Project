package com.cea.auf.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.ColorSpace.Model
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.cea.auf.R
import com.cea.auf.base.MoreDetailActivity
import com.cea.auf.model.JOBSModel
import com.cea.auf.model.SaveJobModel
import com.cea.auf.utils.JobDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.sign


class JobAdapter(
    private var mList: List<JOBSModel.Result>,
    private val context: Context,
    private var database: JobDatabase,

) : RecyclerView.Adapter<JobAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.job_recycle_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var str: String = mList[position].contents
        str = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(str).toString()
        }

        holder.jobTitle.text = mList[position].name
        holder.shortDescription.text = str
        holder.skill.text = mList[position].categories[0].name

        holder.saveJob.setOnClickListener {

            GlobalScope.launch {
                database.contactDao().insertSaveJob(SaveJobModel(mList[position].id.toString(),true,
                    str,
                    mList[position].name,
                    mList[position].categories[0].name))

            }
            Toast.makeText(context, "Save Successfully", Toast.LENGTH_SHORT).show()
        
        }



        holder.button.setOnClickListener {
            var intent = Intent(context, MoreDetailActivity::class.java)
            intent.putExtra("SKILL", mList[position].categories[0].name)
            intent.putExtra("DESCRIPTION", str)
            intent.putExtra("TITLE", mList[position].name)
            intent.putExtra("ID", mList[position].id)
            context.startActivity(intent)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun filterdList(filteredList: ArrayList<JOBSModel.Result>) {

        mList=filteredList
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val button: AppCompatButton = itemView.findViewById(R.id.moreDetailBtn)
        val jobTitle: TextView = itemView.findViewById(R.id.jobTitleTxt)
        val shortDescription: TextView = itemView.findViewById(R.id.shortDesTxt)
        val skill: TextView = itemView.findViewById(R.id.skillsTxt)
        val saveJob: ImageView = itemView.findViewById(R.id.saveJobImg)

    }
}