package com.example.randomuserapp.room

import android.content.Context
import androidx.room.*
import com.example.randomuserapp.Converters
import com.example.randomuserapp.data.Result

@Database(entities = [Result::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase(){

    abstract fun getUserDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase ?= null

        fun getDatabase(context: Context): UserDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}