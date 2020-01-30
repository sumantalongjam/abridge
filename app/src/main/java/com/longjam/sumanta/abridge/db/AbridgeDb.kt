package com.longjam.sumanta.abridge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.longjam.sumanta.abridge.db.dao.CommentDao
import com.longjam.sumanta.abridge.db.dao.IssueDao
import com.longjam.sumanta.abridge.db.entity.Comment
import com.longjam.sumanta.abridge.db.entity.Issue

@Database(entities = [Issue::class, Comment::class], version = 1, exportSchema = false)
abstract class AbridgeDb : RoomDatabase() {

    abstract fun issueDao(): IssueDao
    abstract fun commentDao(): CommentDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AbridgeDb? = null

        fun getDatabase(context: Context): AbridgeDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context, AbridgeDb::class.java, "abridge_db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}