package com.longjam.sumanta.abridge.ui.issue_detail

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.longjam.sumanta.abridge.AppExecutors
import com.longjam.sumanta.abridge.db.AbridgeDb
import com.longjam.sumanta.abridge.db.entity.Comment
import com.longjam.sumanta.abridge.db.entity.Issue
import com.longjam.sumanta.abridge.isOnline
import com.longjam.sumanta.abridge.network.AbridgeApi
import com.longjam.sumanta.abridge.network.ApiEmptyResponse
import com.longjam.sumanta.abridge.network.ApiErrorResponse
import com.longjam.sumanta.abridge.network.ApiSuccessResponse

class IssueDetailViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading = ObservableBoolean()
    var isEmpty = ObservableBoolean(true)
    var message = ObservableField<String>()
    var issue: Issue? = null
    private val commentDao = AbridgeDb.getDatabase(application).commentDao()
    private val appExecutors = AppExecutors()

    fun getAllComment(number: String): LiveData<List<Comment>> {
        val result = MediatorLiveData<List<Comment>>()
        isLoading.set(true)
        message.set("Loading")
        if (isOnline(getApplication())) {
            val apiResponse = AbridgeApi.apiService.getComments(number)
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
                isLoading.set(false)
                when (response) {
                    is ApiSuccessResponse -> {
                        appExecutors.diskIO().execute {
                            saveToDb(response.body)
                            appExecutors.mainThread().execute {
                                isEmpty.set(false)
                                result.value = response.body
                            }
                        }
                    }
                    is ApiEmptyResponse -> {
                        isEmpty.set(true)
                        message.set("No comment available")
                        result.value = null
                    }
                    is ApiErrorResponse -> {
                        isEmpty.set(true)
                        message.set(response.errorMessage)
                        result.value = null
                    }
                }
            }
        } else {
            val data = commentDao.getAllComment(issue?.number)
            result.addSource(data) { response ->
                isLoading.set(false)
                if (response.isEmpty()) {
                    isEmpty.set(true)
                    message.set("No comment available")
                    result.value = null
                } else {
                    isEmpty.set(false)
                    result.value = response
                }
            }
        }
        return result
    }

    private fun saveToDb(comments: List<Comment>) {
        comments.forEach {
            issue?.let { issue ->
                it.number = issue.number
            }
            commentDao.insert(it)
        }
    }
}
