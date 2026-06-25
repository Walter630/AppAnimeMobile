package com.example.princesa_disney.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.princesa_disney.entity.Anime

@Dao
interface AnimeDAO {
    @Insert
    suspend fun insert(anime: List<Anime>): List<Long>

    @Insert
    suspend fun insertSingle(anime: Anime): Long

    @Query("SELECT * FROM Anime")
    suspend fun getAllAnime(): List<Anime>

    @Query("SELECT * FROM Anime WHERE id = :id")
    suspend fun getAnimeId(id: Int): Anime

    @Query("SELECT * FROM Anime WHERE favorite = 1")
    suspend fun getFavoriteAnime(): List<Anime>

    @Update
    suspend fun update(anime: Anime): Int

    @Delete
    suspend fun deleteAnime(anime: Anime): Int
}
