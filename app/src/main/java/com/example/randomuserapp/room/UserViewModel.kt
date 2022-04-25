package com.example.randomuserapp.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allUser: LiveData<List<Result>>

    init {
        val dao = UserDatabase.getDatabase(application).getUserDao()
        repository = UserRepository(dao)
        allUser = repository.allUsers
    }

    fun addUser(user: Result) = viewModelScope.launch(Dispatchers.IO){
        repository.addUser(user)
    }

    fun addAllUser(user: List<Result>) = viewModelScope.launch(Dispatchers.IO){
        repository.addAllUser(user)
    }

    fun deleteUser(user: Result) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteUser(user)
    }
}