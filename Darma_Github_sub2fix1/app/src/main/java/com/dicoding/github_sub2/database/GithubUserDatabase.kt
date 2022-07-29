package com.dicoding.github_sub2.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.dicoding.github_sub2.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class GithubUserDatabase : RoomDatabase() {

    abstract fun githubUserDao(): GithubUserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUserDatabase? = null

        fun getInstance(context: Context): GithubUserDatabase =
            INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                GithubUserDatabase::class.java,
                "Github.db"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }
}