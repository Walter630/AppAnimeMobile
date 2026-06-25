package com.example.princesa_disney.view

import androidx.lifecycle.ViewModel
import com.example.princesa_disney.apiExterna.AnimeApiDto
import com.example.princesa_disney.apiExterna.JikanResponse
import com.example.princesa_disney.apiExterna.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescobrirViewModel : ViewModel() {
    private val service = RetrofitClient.createAnimeApiService()

    private val _resultados = MutableStateFlow<List<AnimeApiDto>>(emptyList())
    val resultados: StateFlow<List<AnimeApiDto>> = _resultados.asStateFlow()

    private val _carregando = MutableStateFlow(false)
    val carregando: StateFlow<Boolean> = _carregando.asStateFlow()

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro.asStateFlow()

    fun buscarAnimes(termo: String) {
        _carregando.value = true
        _erro.value = null
        service.buscarAnimes(termo).enqueue(object : Callback<JikanResponse> {
            override fun onResponse(call: Call<JikanResponse>, response: Response<JikanResponse>) {
                _carregando.value = false
                if (response.isSuccessful) _resultados.value = response.body()?.data ?: emptyList()
                else _erro.value = "Erro ao buscar (${response.code()})"
            }
            override fun onFailure(call: Call<JikanResponse>, t: Throwable) {
                _carregando.value = false
                _erro.value = "Falha de conexão: ${t.message}"
            }
        })
    }
}