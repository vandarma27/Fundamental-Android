package com.dicoding.github_sub2.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github_sub2.R
import com.dicoding.github_sub2.adapter.SearchAdapter
import com.dicoding.github_sub2.database.GithubUserDatabase
import com.dicoding.github_sub2.databinding.ActivityMainBinding
import com.dicoding.github_sub2.helper.SettingPreferences
import com.dicoding.github_sub2.models.User
import com.dicoding.github_sub2.ui.favorite.FavoriteActivity
import com.dicoding.github_sub2.ui.setting.SettingActivity
import com.dicoding.github_sub2.ui.setting.SettingActivity.Companion.dataStore
import com.dicoding.github_sub2.viewmodel.SearchViewModel
import com.dicoding.github_sub2.viewmodel.SettingViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private val settingViewModel: SettingViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val pref = SettingPreferences.getInstance(applicationContext.dataStore)
                return SettingViewModel(pref) as T
            }
        }
    }
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()

        settingViewModel.getThemeSettings().observe(this) {
            AppCompatDelegate.setDefaultNightMode(it)
        }

        searchViewModel.listUsers.observe(this) { listUsers ->
            setUserData(listUsers)
        }

        searchViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun recyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        adapter = SearchAdapter(arrayListOf())
        binding.rvUsers.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.setSearchUsers(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    searchViewModel.setSearchUsers(newText)
                    searchView.clearFocus()
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_favorite -> {
                Intent(applicationContext, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.menu_setting -> {
                Intent(applicationContext, SettingActivity::class.java).also {
                    startActivity (it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setUserData(user: List<User>) {
        adapter.setUser(user)
        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(user: String) {
                showUser(user)
            }
        })
    }

    private fun showUser(username: String) {
        val UserIntent = Intent(this@MainActivity, DetailActivity::class.java)
        UserIntent.putExtra("EXTRA_LOGIN", username)
        startActivity(UserIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
        }
    }

}