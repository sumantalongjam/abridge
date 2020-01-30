package com.longjam.sumanta.abridge.ui.issue_list

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.longjam.sumanta.abridge.AppExecutors
import com.longjam.sumanta.abridge.db.AbridgeDb
import com.longjam.sumanta.abridge.db.entity.Issue
import com.longjam.sumanta.abridge.isOnline
import com.longjam.sumanta.abridge.network.*

class IssueListViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading = ObservableBoolean()
    private val issueDao = AbridgeDb.getDatabase(application).issueDao()
    private val appExecutors = AppExecutors()

    fun getAllIssues(): LiveData<List<Issue>> {
        val result = MediatorLiveData<List<Issue>>()
        isLoading.set(true)
        if (isOnline(getApplication())) {
            val apiResponse = AbridgeApi.apiService.getIssues()
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
                isLoading.set(false)
                when (response) {
                    is ApiSuccessResponse -> {
                        appExecutors.diskIO().execute {
                            saveToDb(response.body)
                            appExecutors.mainThread().execute {
                                result.value = response.body
                            }
                        }
                    }
                    is ApiEmptyResponse -> {
                        result.value = null
                    }
                    is ApiErrorResponse -> {
                        result.value = null
                    }
                }
            }
        } else {
            val data = issueDao.getAllIssue()
            result.addSource(data) { response ->
                isLoading.set(false)
                if (response.isEmpty()) {
                    result.value = null
                } else {
                    result.value = response
                }
            }
        }
        return result
    }

    private fun saveToDb(issues: List<Issue>) {
        issueDao.insertAll(issues)
    }
}