package com.example.princesa_disney.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.princesa_disney.entity.Anime
import com.example.princesa_disney.entity.User

@Database(entities = [Anime::class, User::class], version = 3, exportSchema = false)
abstract class AnimeDataBase : RoomDatabase() {
    abstract fun animeDao(): AnimeDAO
    abstract fun userDao(): UserDao
    companion object {
        private lateinit var instance: AnimeDataBase //construtor do nosso sistema
        private const val DATABASE_NAME = "anime_db"

        fun getDatabase(context: Context): AnimeDataBase { //contexto de onde esta vindo ou de onde agente quer q tenha
            if (!::instance.isInitialized) { //se a variavel instacia foi inicializada
                synchronized(this) {
                    instance = Room.databaseBuilder(context, AnimeDataBase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()  // ← limpa banco ao mudar versão
                        .build() //construa
                }
            }
            return instance
        }
    }
}
