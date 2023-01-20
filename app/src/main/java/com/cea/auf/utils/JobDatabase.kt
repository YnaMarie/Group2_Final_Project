package com.cea.auf.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cea.auf.model.CategoryCovertor
import com.cea.auf.model.Convertor
import com.cea.auf.model.JOBSModel
import com.cea.auf.model.SaveJobModel


@Database(entities = [JOBSModel::class,SaveJobModel::class], version = 1)
@TypeConverters(Convertor::class,CategoryCovertor::class)
abstract class JobDatabase : RoomDatabase() {
    abstract fun contactDao(): Dao

    companion object {



        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        JobDatabase::class.java,
                        "job_db")
                        .build()
                }

            }
            return INSTANCE!!
        }
    }
}