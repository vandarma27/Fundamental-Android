package com.dicoding.github_sub2.api.model

import com.google.gson.annotations.SerializedName

data class FollowResponse(

    @field:SerializedName("FollowersResponse")
    val followersResponse: List<FollowerDto>
)
