package com.dicoding.github_sub2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.github_sub2.database.entity.UserEntity
import com.dicoding.github_sub2.database.entity.UpdateUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {

    @Query("SELECT * FROM users")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users where is_favorite = 1")
    fun getFavoriteUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users where login = :login")
    fun getUserByLogin(login: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM users where id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM users where id = :id")
    fun getLiveUserById(id: Int): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(user: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update(entity = UserEntity::class)
    suspend fun updateUser(user: UpdateUserEntity)

    @Update
    suspend fun updateFavoriteUser(user: UserEntity)
}
