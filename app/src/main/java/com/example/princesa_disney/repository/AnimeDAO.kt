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
    fun insert(anime: List<Anime>)
    @Insert
    fun insertSingle(anime: Anime): Long

    @Query("SELECT * FROM Anime")
    fun getAllAnime(): List<Anime>

    @Query("SELECT * FROM Anime WHERE id = :id")
    fun getAnimeId(id: Int): Anime

    @Query("SELECT * FROM Anime WHERE favorite = 1")
    fun getFavoriteAnime(): List<Anime>

    @Update
    fun update(anime: Anime)

    @Delete
    fun deleteAnime(anime: Anime): Int
}