package com.example.randomuserapp.room

import androidx.lifecycle.LiveData
import com.example.randomuserapp.data.Result

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<Result>> = userDao.readAllUsers()

    suspend fun addUser(user: Result){
        userDao.addUser(user)
    }

    suspend fun addAllUser(user: List<Result>){
        userDao.addAllUser(user)
    }

    suspend fun deleteUser(user: Result){
        userDao.deleteUser(user)
    }
}