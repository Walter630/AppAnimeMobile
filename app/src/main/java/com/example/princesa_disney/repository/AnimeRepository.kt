package com.example.princesa_disney.repository

import android.content.Context
import com.example.princesa_disney.entity.Anime

class AnimeRepository private constructor(context: Context) {

    private var dataBase = AnimeDataBase.getDatabase(context).animeDao()

    companion object {
        @Volatile
        private var INSTANCE: AnimeRepository? = null

        fun getInstance(context: Context): AnimeRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AnimeRepository(context).also { INSTANCE = it }
            }
        }
    }

    private fun getInitialAnime(): List<Anime> {
        return listOf(

            Anime(
                id = 1,
                name = "Naruto",
                description = "Um jovem ninja sonha em se tornar Hokage e conquistar o respeito da vila.",
                epsodios = 220,
                favorite = true,
                imageUrl = "https://picsum.photos/seed/naruto/400/600"
            ),

            Anime(
                id = 2,
                name = "One Piece",
                description = "Luffy e sua tripulação viajam pelos mares em busca do lendário tesouro One Piece.",
                epsodios = 1100,
                favorite = true,
                imageUrl = "https://picsum.photos/seed/onepiece/400/600"
            ),

            Anime(
                id = 3,
                name = "Attack on Titan",
                description = "A humanidade luta para sobreviver contra gigantes conhecidos como Titãs.",
                epsodios = 89,
                favorite = true,
                imageUrl = "https://picsum.photos/seed/titan/400/600"
            ),

            Anime(
                id = 4,
                name = "Death Note",
                description = "Um estudante encontra um caderno capaz de matar qualquer pessoa cujo nome seja escrito nele.",
                epsodios = 37,
                favorite = false
            ),

            Anime(
                id = 5,
                name = "Demon Slayer",
                description = "Tanjiro luta contra demônios para salvar sua irmã e vingar sua família.",
                epsodios = 55,
                favorite = true
            ),

            Anime(
                id = 6,
                name = "Dragon Ball Z",
                description = "Goku protege a Terra de inimigos poderosos enquanto supera seus limites.",
                epsodios = 291,
                favorite = true
            ),

            Anime(
                id = 7,
                name = "Fullmetal Alchemist: Brotherhood",
                description = "Dois irmãos alquimistas buscam recuperar seus corpos após um experimento proibido.",
                epsodios = 64,
                favorite = false
            ),

            Anime(
                id = 8,
                name = "Jujutsu Kaisen",
                description = "Yuji Itadori entra no mundo das maldições após consumir um objeto amaldiçoado.",
                epsodios = 47,
                favorite = true
            ),

            Anime(
                id = 9,
                name = "Tokyo Ghoul",
                description = "Kaneki se transforma em meio-ghoul após um acidente e precisa sobreviver em dois mundos.",
                epsodios = 48,
                favorite = false
            ),

            Anime(
                id = 10,
                name = "Sword Art Online",
                description = "Jogadores ficam presos em um MMORPG onde morrer no jogo significa morrer na vida real.",
                epsodios = 96,
                favorite = false
            )
        )
    }

    fun loadInitialData() {
        val animes = getInitialAnime()
        dataBase.insert(animes)
    }

    fun getAllAnime(): List<Anime> {
        return dataBase.getAllAnime()
    }

    fun getFavoriteAnime(): List<Anime> {
        return dataBase.getFavoriteAnime()
    }
    fun insertAnime(anime: Anime): Boolean = dataBase.insertSingle(anime) > 0  // ← NOVO
    fun getAnimeId(id: Int): Anime {
        return dataBase.getAnimeId(id)
    }
    fun updateAnime(anime: Anime) {
        return dataBase.update(anime)
    }
    fun deleteAnime(id: Int): Boolean {
        return dataBase.deleteAnime(getAnimeId(id)) > 0
    }

}