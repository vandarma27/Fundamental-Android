package com.dicoding.github_sub2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.github_sub2.api.ApiConfig
import com.dicoding.github_sub2.api.model.DetailUserDto
import com.dicoding.github_sub2.api.model.FollowerDto
import com.dicoding.github_sub2.database.GithubUserDao
import com.dicoding.github_sub2.database.entity.UserEntity
import com.dicoding.github_sub2.models.Follower
import com.dicoding.github_sub2.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(val dao: GithubUserDao) : ViewModel() {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private var _userlogin = MutableLiveData("login")
    private val _detailData = MutableLiveData<User?>()
    val detailData: LiveData<User?> = _detailData
    private val _followersData = MutableLiveData<List<Follower>?>()
    val followersData: LiveData<List<Follower>?> = _followersData
    private val _followingData = MutableLiveData<List<Follower>?>()
    val followingData: LiveData<List<Follower>?> = _followingData

    private val _isloading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isloading

    fun detailUser(username: String) {
        _userlogin.value = username
        _isloading.value = true
        Log.e(TAG, "query: $username")
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<DetailUserDto> {
            override fun onResponse(
                call: Call<DetailUserDto>,
                response: Response<DetailUserDto>
            ) {
                _isloading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                updateUser(responseBody.toUser())
                            }
                        }
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<DetailUserDto>, t: Throwable) {
                _isloading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowers() {
        _isloading.value = true
        val client = ApiConfig.getApiService().getFollowers(_userlogin.value.toString())
        client.enqueue(object : Callback<List<FollowerDto>> {
            override fun onResponse(
                call: Call<List<FollowerDto>>,
                response: Response<List<FollowerDto>>
            ) {
                _isloading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followersData.value = responseBody.map { it.toFollower() }
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowerDto>>, t: Throwable) {
                _isloading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowing() {
        _isloading.value = true
        val client = ApiConfig.getApiService().getFollowing(_userlogin.value.toString())
        client.enqueue(object : Callback<List<FollowerDto>> {
            override fun onResponse(
                call: Call<List<FollowerDto>>,
                response: Response<List<FollowerDto>>
            ) {
                _isloading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followingData.value = responseBody.map { it.toFollower() }
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowerDto>>, t: Throwable) {
                _isloading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun setFavoriteUser(user: User) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    user.apply { isFavorite = !isFavorite }
                    if (dao.getUserById(user.userId) != null) dao.updateFavoriteUser(user.toEntity())
                    else dao.insertUser(user.toEntity())

                    val userEntity = dao.getUserById(user.userId)

                    withContext(Dispatchers.Main) {
                        _detailData.value = userEntity?.toUser()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "setFavoriteUser: ", e)
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    if (dao.getUserById(user.userId) != null) dao.updateUser(user.toUpdateEntity())
                    else dao.insertUser(user.toEntity())

                    val userEntity = dao.getUserById(user.userId)

                    withContext(Dispatchers.Main) {
                        _detailData.value = userEntity?.toUser()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "updateUser: ", e)
            }
        }
    }
}