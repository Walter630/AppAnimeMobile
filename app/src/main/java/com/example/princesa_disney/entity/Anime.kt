package com.example.princesa_disney.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Anime")
data class Anime(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo("epsodios")
    val epsodios: Int,

    @ColumnInfo("favorite")
    val favorite: Boolean,

    @ColumnInfo("Image")
    val imageUrl: String = ""
)