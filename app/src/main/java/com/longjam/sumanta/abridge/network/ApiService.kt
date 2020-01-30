package com.longjam.sumanta.abridge.network

import androidx.lifecycle.LiveData
import com.longjam.sumanta.abridge.db.entity.Comment
import com.longjam.sumanta.abridge.db.entity.Issue
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/repos/square/okhttp/issues")
    fun getIssues(): LiveData<ApiResponse<List<Issue>>>

    @GET("/repos/square/okhttp/issues/{number}/comments")
    fun getComments(@Path("number") number: String): LiveData<ApiResponse<List<Comment>>>
}