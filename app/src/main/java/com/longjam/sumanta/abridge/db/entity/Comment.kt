package com.longjam.sumanta.abridge.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "comment_table", indices = [Index("id")], primaryKeys = ["id"])
data class Comment(
    @SerializedName("id")
    val id: Long,
    @SerializedName("user")
    @Embedded(prefix = "user_")
    val user: User,
    @SerializedName("body")
    var body: String,
    var number: Long
) : Serializable {

    data class User(
        @SerializedName("id")
        val id: Long,
        @SerializedName("login")
        val login: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
    ) : Serializable
}