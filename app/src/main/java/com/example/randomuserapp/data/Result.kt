package com.example.randomuserapp.data

import androidx.room.*

@Entity(tableName = "users_table")
data class Result(
    @PrimaryKey(autoGenerate = true)
    var idd: Int = 0,
//    val cell: String,
    @Embedded
    val dob: Dob,
//    val email: String,
//    val gender: String,
//    val id: Id,
    @Embedded
    val location: Location,
//    val login: Login,
    @Embedded
    val name: Name,
//    val nat: String,
    val phone: String,
    @Embedded
    val picture: Picture,
//    val profile: Bitmap

//    val registered: Registered
)
