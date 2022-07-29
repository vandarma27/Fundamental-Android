package com.dicoding.github_sub2.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.github_sub2.R
import com.dicoding.github_sub2.databinding.ActivitySettingBinding
import com.dicoding.github_sub2.helper.SettingPreferences
import com.dicoding.github_sub2.viewmodel.SettingViewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel: SettingViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val pref = SettingPreferences.getInstance(applicationContext.dataStore)
                return SettingViewModel(pref) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.setting)

        with(binding) {
            when(AppCompatDelegate.getDefaultNightMode()) {
                AppCompatDelegate.MODE_NIGHT_YES -> rbDark.isChecked = true
                AppCompatDelegate.MODE_NIGHT_NO -> rbLight.isChecked = true
                else -> rbSystem.isChecked = true
            }
        }

        settingViewModel.getThemeSettings().observe(this) {
            AppCompatDelegate.setDefaultNightMode(it)
        }

        with(binding) {
            rgAppTheme.setOnCheckedChangeListener { radioGroup, viewId ->
                when (viewId) {
                    R.id.rb_dark -> settingViewModel.saveThemeSetting(AppCompatDelegate.MODE_NIGHT_YES)
                    R.id.rb_light -> settingViewModel.saveThemeSetting(AppCompatDelegate.MODE_NIGHT_NO)
                    else -> settingViewModel.saveThemeSetting(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}