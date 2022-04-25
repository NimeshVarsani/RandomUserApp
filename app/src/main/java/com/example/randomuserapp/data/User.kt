package com.example.randomuserapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "users_table")
//data class User(
//    val info: Info,
//    val results: List<Result>,
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//)
//data class
data class User(
    val info: Info,
    val results: List<Result>
)