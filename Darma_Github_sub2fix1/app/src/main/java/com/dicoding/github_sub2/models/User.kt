package com.dicoding.github_sub2.models

import com.dicoding.github_sub2.database.entity.UpdateUserEntity
import com.dicoding.github_sub2.database.entity.UserEntity

data class User(
    val userId: Int,
    val followers: Int = 0,
    val avatarUrl: String = "",
    val followingUrl: String = "",
    val following: Int = 0,
    val name: String = "",
    val company: String = "",
    val location: String = "",
    val login: String = "",
    val organizationsUrl: String = "",
    val followersUrl: String = "",
    var isFavorite: Boolean = false
) {
    fun toUpdateEntity() = UpdateUserEntity(
        id = userId,
        followers = followers,
        avatarUrl = avatarUrl,
        followingUrl = followingUrl,
        following = following,
        name = name,
        company = company,
        location = location,
        login = login,
        organizationsUrl = organizationsUrl,
        followersUrl = followersUrl,
    )

    fun toEntity() = UserEntity(
        id = userId,
        followers = followers,
        avatarUrl = avatarUrl,
        followingUrl = followingUrl,
        following = following,
        name = name,
        company = company,
        location = location,
        login = login,
        organizationsUrl = organizationsUrl,
        followersUrl = followersUrl,
        isFavorite = isFavorite,
    )
}
