package com.cea.auf.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cea.auf.R
import com.cea.auf.base.MoreDetailActivity
import com.cea.auf.model.JOBSModel
import com.cea.auf.model.SaveJobModel
import com.cea.auf.utils.JobDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SaveJobAdapter(private var mList: ArrayList<SaveJobModel>, private var context: Context, private var database: JobDatabase) :
    RecyclerView.Adapter<SaveJobAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.job_recycle_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.jobTitle.text = mList[position].title
        holder.shortDescription.text = mList[position].description
        holder.skill.text = mList[position].skill
        holder.saveJob.backgroundTintList = ContextCompat.getColorStateList(context, R.color.green)


        holder.button.setOnClickListener {
            var intent = Intent(context, MoreDetailActivity::class.java)
            intent.putExtra("SKILL", mList[position].skill)
            intent.putExtra("DESCRIPTION", mList[position].description)
            intent.putExtra("TITLE", mList[position].title)
            intent.putExtra("ID", mList[position].id)

            context.startActivity(intent)

        }
        holder.saveJob.setOnClickListener {
            GlobalScope.launch {
                database.contactDao().deleteJob(mList[position].id)

            }
            Toast.makeText(context, "Remove Successfully", Toast.LENGTH_SHORT).show()

            mList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,mList.size)
        }
    }

    override fun getItemCount(): Int {
        return mList.size

    }
    @SuppressLint("NotifyDataSetChanged")
    fun filterdList(filteredList: ArrayList<SaveJobModel>) {

        mList=filteredList
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val button: AppCompatButton = itemView.findViewById(R.id.moreDetailBtn)
        val jobTitle: TextView = itemView.findViewById(R.id.jobTitleTxt)
        val shortDescription: TextView = itemView.findViewById(R.id.shortDesTxt)
        val skill: TextView = itemView.findViewById(R.id.skillsTxt)
        val saveJob: ImageView = itemView.findViewById(R.id.saveJobImg)
    }
}