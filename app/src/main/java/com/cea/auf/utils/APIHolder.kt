package com.cea.auf.utils

import com.cea.auf.model.JOBSModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIHolder {
    @GET("jobs")
    fun getJobs(
        @Query("page") page: String?,
    ): Call<JOBSModel?>?
}