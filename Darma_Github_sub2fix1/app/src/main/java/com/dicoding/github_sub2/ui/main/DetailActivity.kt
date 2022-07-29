package com.dicoding.github_sub2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.github_sub2.R
import com.dicoding.github_sub2.database.GithubUserDatabase
import com.dicoding.github_sub2.databinding.ActivityDetailBinding
import com.dicoding.github_sub2.models.User
import com.dicoding.github_sub2.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val instance = GithubUserDatabase.getInstance(applicationContext)
                return DetailViewModel(instance.githubUserDao()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewpager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val userName = intent.getStringExtra("EXTRA_LOGIN") ?: return onBackPressed()
        title = userName

        detailViewModel.detailData.observe(this) { detailData ->
            setDetailData(detailData)
        }

        detailViewModel.isloading.observe(this) {
            showLoading(it)
        }

        detailViewModel.detailUser(userName)
    }

    private fun setDetailData(detailData: User?) {
        Glide.with(this)
            .load(detailData?.avatarUrl)
            .into(binding.imgDetailAvatar)

        binding.apply {
            tvDetailUsername.text = detailData?.login
            tvDetailName.text = detailData?.name
            tvDetailCompany.text = detailData?.company
            tvDetailLocation.text = detailData?.login
            tvDetailFollower.text = String.format("${detailData?.followers} Followers")
            tvDetailFollowing.text = String.format("${detailData?.following} Following")

            fabFavorite.setImageResource(if (detailData?.isFavorite == true)
                R.drawable.ic_baseline_favorite_24
            else
                R.drawable.ic_baseline_unfavorite_border_24
            )

            detailData?.let { user ->
                fabFavorite.setOnClickListener {
                    detailViewModel.setFavoriteUser(user)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.detailProgresBar.visibility = View.VISIBLE
        } else {
            binding.detailProgresBar.visibility = View.INVISIBLE
        }
    }
}