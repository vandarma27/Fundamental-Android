package com.dicoding.github_sub2.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github_sub2.adapter.SearchAdapter
import com.dicoding.github_sub2.database.GithubUserDatabase
import com.dicoding.github_sub2.databinding.FragmentFollowingBinding
import com.dicoding.github_sub2.models.Follower
import com.dicoding.github_sub2.models.User
import com.dicoding.github_sub2.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followingAdapter: SearchAdapter
    private val detailViewModel: DetailViewModel by activityViewModels(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val instance = GithubUserDatabase.getInstance(requireContext())
                return DetailViewModel(instance.githubUserDao()) as T
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)

        recyclerView()

        detailViewModel.followingData.observe(requireActivity()) { followingData ->
            if (followingData != null) {
                setFollowingData(followingData.map { it.toUser() })
            }
        }


        detailViewModel.getFollowing()

        return binding.root
    }

    private fun recyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        followingAdapter = SearchAdapter(arrayListOf())
        binding.rvFollowing.adapter = followingAdapter
    }

    private fun setFollowingData(data: List<User>) {
        followingAdapter.setUser(data)
        followingAdapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(user: String) {
                detailViewModel.apply {
                    detailUser(user)
                    getFollowing()
                }
            }

        })
    }
}