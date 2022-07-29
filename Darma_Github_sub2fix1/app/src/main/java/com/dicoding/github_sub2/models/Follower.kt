package com.dicoding.github_sub2.models

data class Follower(
    val id: Int,
    val avatarUrl: String,
    val eventsUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val gravatarId: String,
    val htmlUrl: String,
    val login: String,
    val nodeId: String,
    val organizationsUrl: String,
    val receivedEventsUrl: String,
    val reposUrl: String,
    val siteAdmin: Boolean,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val type: String,
    val url: String,
) {
    fun toUser() = User(
        userId = id,
        avatarUrl = avatarUrl,
        followingUrl = followingUrl,
        login = login,
        organizationsUrl = organizationsUrl,
        followersUrl = followersUrl,
    )
}