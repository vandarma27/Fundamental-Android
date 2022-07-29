package com.dicoding.github_sub2.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github_sub2.R
import com.dicoding.github_sub2.adapter.FavoriteAdapter
import com.dicoding.github_sub2.adapter.SearchAdapter
import com.dicoding.github_sub2.database.GithubUserDatabase
import com.dicoding.github_sub2.databinding.ActivityFavoriteBinding
import com.dicoding.github_sub2.models.User
import com.dicoding.github_sub2.ui.main.DetailActivity
import com.dicoding.github_sub2.viewmodel.FavoriteViewModel
import com.dicoding.github_sub2.viewmodel.SearchViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val searchViewModel by viewModels<FavoriteViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val instance = GithubUserDatabase.getInstance(applicationContext)
                return FavoriteViewModel(instance.githubUserDao()) as T
            }
        }

    })
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()

        searchViewModel.listUsers.observe(this) { listUsers ->
            setUserData(listUsers)
        }

        title = getString(R.string.favorite)
    }

    private fun recyclerView() {
        adapter = FavoriteAdapter()

        val layoutManager = GridLayoutManager(this, 2)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = adapter
    }

    private fun setUserData(user: List<User>) {
        adapter.setUser(user)
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(user: String) {
                showUser(user)
            }
        })
    }

    private fun showUser(username: String) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("EXTRA_LOGIN", username)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
        }
    }
}