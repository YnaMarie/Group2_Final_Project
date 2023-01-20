package com.cea.auf.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "save_job")

class SaveJobModel(@PrimaryKey var id:String,var status:Boolean, var description:String,var title:String,var skill:String)  {
}