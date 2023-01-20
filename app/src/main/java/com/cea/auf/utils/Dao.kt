package com.cea.auf.utils

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cea.auf.model.JOBSModel
import com.cea.auf.model.SaveJobModel

@androidx.room.Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(contact:JOBSModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaveJob(contact:SaveJobModel)


//
    @Query("Delete from save_job where id=:id")
    suspend fun deleteJob(id:String)

    @Query("Select * from JOB")
    fun getJob(): LiveData<JOBSModel>



    @Query("Select * from save_job")
    fun getSaveJob(): LiveData<List<SaveJobModel>>
}