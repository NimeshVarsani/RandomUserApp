package com.example.randomuserapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.randomuserapp.data.Result

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: Result)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllUser(user: List<Result>)

    @Query("SELECT * FROM users_table ORDER BY idd ASC")
    fun readAllUsers(): LiveData<List<Result>>

    @Delete
    suspend fun deleteUser(user: Result)
}