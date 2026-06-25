package com.example.princesa_disney.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.princesa_disney.entity.Anime
import com.example.princesa_disney.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AnimeRepository.getInstance(application.applicationContext)

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem: StateFlow<String?> = _mensagem.asStateFlow()

    init {
        viewModelScope.launch {
            if (repository.getAllAnime().isEmpty()) repository.loadInitialData()
            carregarAnimes()
        }
    }

    private suspend fun carregarAnimes() {
        _animeList.value = repository.getAllAnime()
    }

    fun inserirAnime(nome: String, descricao: String, episodios: Int, favorite: Boolean, imageUrl: String = "") {
        viewModelScope.launch {
            val anime = Anime(name = nome, description = descricao, epsodios = episodios, favorite = favorite, imageUrl = imageUrl)
            if (repository.insertAnime(anime)) {
                _mensagem.value = "Anime adicionado!"
                carregarAnimes()
            }
        }
    }

    fun atualizarAnime(anime: Anime) {
        viewModelScope.launch {
            if (repository.updateAnime(anime)) {
                _mensagem.value = "Anime atualizado!"
                carregarAnimes()
            }
        }
    }

    fun deletarAnime(id: Int) {
        viewModelScope.launch {
            if (repository.deleteAnime(id)) {
                _mensagem.value = "Anime removido!"
                carregarAnimes()
            }
        }
    }

    fun limparMensagem() { _mensagem.value = null }
}
