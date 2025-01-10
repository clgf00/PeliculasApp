package com.example.claudiagalerapract2.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.claudiagalerapract2.data.local.modelo.UserEntity


@Dao
interface UserDao {

    @Query("SELECT * FROM users order by username")
    fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUserByUsernameAndPassword(username: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): UserEntity?
}
