package com.example.claudiagalerapract2.data.local.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.claudiagalerapract2.domain.modelo.User

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int =0,
    val username : String,
    val password : String

)

fun UserEntity.toUser() : User = User(
    id = this.id,
    username = this.username,
    password= this.password
)