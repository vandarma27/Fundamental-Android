package com.dicoding.github_sub2.api.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("items")
    val items: List<UserDto>
)

