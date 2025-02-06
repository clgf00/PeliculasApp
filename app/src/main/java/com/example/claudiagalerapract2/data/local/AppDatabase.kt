package com.example.claudiagalerapract2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.claudiagalerapract2.data.local.modelo.UserEntity

@Database(entities = [UserEntity::class], version = 4, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}