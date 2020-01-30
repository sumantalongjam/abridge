package com.longjam.sumanta.abridge.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "issue_table", indices = [Index("id")], primaryKeys = ["id"])
data class Issue(
    @SerializedName("id")
    val id: Long,
    @SerializedName("number")
    val number: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("user")
    @Embedded(prefix = "user_")
    val user: User,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("body")
    var body: String
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