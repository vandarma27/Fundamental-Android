package com.dicoding.github_sub2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.github_sub2.database.GithubUserDao
import com.dicoding.github_sub2.models.User

class FavoriteViewModel(private val dao: GithubUserDao) : ViewModel() {

    val listUsers: LiveData<List<User>> get() = Transformations.map(dao.getFavoriteUser()) { list ->
        list.map {
            it.toUser()
        }
    }

}