package com.cea.auf.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "job")
data class JOBSModel(

    @PrimaryKey

    var page: Int,


    var results: List<Result>,

)  {
  data class Result(
      var status:Boolean,
       var categories: List<Category>,
       var contents: String,
       var id: Int,

       var name: String,
    ) {
        data class Category(
            var name: String
        )


    }


}