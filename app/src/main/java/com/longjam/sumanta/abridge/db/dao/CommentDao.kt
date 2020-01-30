package com.longjam.sumanta.abridge.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.longjam.sumanta.abridge.db.entity.Comment

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments: List<Comment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment)

    @Query("SELECT * FROM comment_table WHERE number = :inputNumber")
    fun getAllComment(inputNumber: Long? = 0): LiveData<List<Comment>>

    @Query("DELETE FROM comment_table")
    fun deleteAll()
}