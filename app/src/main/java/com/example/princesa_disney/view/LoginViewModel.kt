package com.example.princesa_disney.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.princesa_disney.entity.User
import com.example.princesa_disney.repository.AnimeDataBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AnimeDataBase.getDatabase(application).userDao() // ajusta o nome do getInstance conforme o teu AppDatabase

    private val _logado = MutableStateFlow(false)
    val logado: StateFlow<Boolean> = _logado.asStateFlow()
    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro.asStateFlow()

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            val usuario = userDao.login(email.trim(), senha)
            if (usuario != null) { _logado.value = true; _erro.value = null }
            else _erro.value = "E-mail ou senha incorretos"
        }
    }

    fun cadastrar(email: String, senha: String) {
        viewModelScope.launch {
            if (userDao.buscarPorEmail(email.trim()) != null) {
                _erro.value = "Já existe uma conta com esse e-mail"; return@launch
            }
            userDao.cadastrar(User(email = email.trim(), senha = senha))
            _logado.value = true
        }
    }
}