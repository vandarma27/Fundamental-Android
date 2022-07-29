package com.dicoding.github_sub2.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.github_sub2.models.User
import com.google.gson.annotations.SerializedName
import kotlin.math.log

@Entity
data class UpdateUserEntity(
    @PrimaryKey
    @NonNull
    val id: Int,

    @ColumnInfo(name = "followers")
    val followers: Int = 0,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String = "",

    @ColumnInfo(name = "following_url")
    val followingUrl: String = "",

    @ColumnInfo(name = "following")
    val following: Int = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "company")
    val company: String = "",

    @ColumnInfo(name = "location")
    val location: String = "",

    @ColumnInfo(name = "login")
    val login: String = "",

    @ColumnInfo(name = "organizations_url")
    val organizationsUrl: String = "",

    @ColumnInfo(name = "followers_url")
    val followersUrl: String = "",
)
