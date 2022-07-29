package com.dicoding.github_sub2.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github_sub2.adapter.SearchAdapter
import com.dicoding.github_sub2.database.GithubUserDatabase
import com.dicoding.github_sub2.databinding.FragmentFollowerBinding
import com.dicoding.github_sub2.models.User
import com.dicoding.github_sub2.viewmodel.DetailViewModel

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var followersAdapter: SearchAdapter
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
        binding = FragmentFollowerBinding.inflate(inflater, container, false)

        recyclerView()

        detailViewModel.followersData.observe(requireActivity()) { followerData ->
            if (followerData != null) {
                setFollowersData(followerData.map { it.toUser() })
            }
        }

        detailViewModel.getFollowers()

        return binding.root
    }

    private fun recyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        followersAdapter = SearchAdapter(arrayListOf())
        binding.rvFollowers.adapter = followersAdapter
    }

    private fun setFollowersData(data: List<User>) {
        followersAdapter.setUser(data)
        followersAdapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(user: String) {
                detailViewModel.apply {
                    detailUser(user)
                    getFollowers()
                }
            }

        })


    }

}