package com.longjam.sumanta.abridge.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.longjam.sumanta.abridge.db.entity.Issue

@Dao
interface IssueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(issues: List<Issue>)

    @Query("SELECT * FROM issue_table ORDER BY id DESC")
    fun getAllIssue(): LiveData<List<Issue>>

    @Query("DELETE FROM issue_table")
    fun deleteAll()
}