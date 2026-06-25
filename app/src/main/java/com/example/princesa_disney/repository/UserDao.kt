package com.example.princesa_disney.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.princesa_disney.entity.User


@Dao
interface UserDao {
    @Insert
    suspend fun cadastrar(usuario: User): Long

    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): User?

    @Query("SELECT * FROM User WHERE email = :email AND senha = :senha LIMIT 1")
    suspend fun login(email: String, senha: String): User?
}
