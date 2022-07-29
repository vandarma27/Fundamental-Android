package com.dicoding.github_sub2.api

import com.dicoding.github_sub2.api.model.DetailUserDto
import com.dicoding.github_sub2.api.model.FollowerDto
import com.dicoding.github_sub2.api.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<DetailUserDto>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowerDto>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowerDto>>
}