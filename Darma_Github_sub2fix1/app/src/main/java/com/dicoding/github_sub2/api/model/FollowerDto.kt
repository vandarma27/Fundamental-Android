package com.dicoding.github_sub2.api.model


import com.dicoding.github_sub2.models.Follower
import com.dicoding.github_sub2.models.User
import com.google.gson.annotations.SerializedName

data class FollowerDto(
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("events_url")
    val eventsUrl: String? = null,

    @SerializedName("followers_url")
    val followersUrl: String? = null,

    @SerializedName("following_url")
    val followingUrl: String? = null,

    @SerializedName("gists_url")
    val gistsUrl: String? = null,

    @SerializedName("gravatar_id")
    val gravatarId: String? = null,

    @SerializedName("html_url")
    val htmlUrl: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("login")
    val login: String? = null,

    @SerializedName("node_id")
    val nodeId: String? = null,

    @SerializedName("organizations_url")
    val organizationsUrl: String? = null,

    @SerializedName("received_events_url")
    val receivedEventsUrl: String? = null,

    @SerializedName("repos_url")
    val reposUrl: String? = null,

    @SerializedName("site_admin")
    val siteAdmin: Boolean? = null,

    @SerializedName("starred_url")
    val starredUrl: String? = null,

    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("url")
    val url: String? = null,
) {
    fun toFollower() = Follower(
        id = id ?: 0,
        avatarUrl = avatarUrl ?: "",
        eventsUrl = eventsUrl ?: "",
        followersUrl = followersUrl ?: "",
        followingUrl = followingUrl ?: "",
        gistsUrl = gistsUrl ?: "",
        gravatarId = gravatarId ?: "",
        htmlUrl = htmlUrl ?: "",
        login = login ?: "",
        nodeId = nodeId ?: "",
        organizationsUrl = organizationsUrl ?: "",
        receivedEventsUrl = receivedEventsUrl ?: "",
        reposUrl = reposUrl ?: "",
        siteAdmin = siteAdmin ?: false,
        starredUrl = starredUrl ?: "",
        subscriptionsUrl = subscriptionsUrl ?: "",
        type = type ?: "",
        url = url ?: ""
    )
}