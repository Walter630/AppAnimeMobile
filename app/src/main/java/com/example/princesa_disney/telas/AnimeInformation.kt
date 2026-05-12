package com.example.princesa_disney.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.princesa_disney.Anime
import com.example.princesa_disney.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInformationAnime() {
    val listaAnimes = listOf(
        Anime(
            "Naruto",
            "220 Episódios",
            "Um jovem ninja busca reconhecimento e o sonho de se tornar Hokage.",
            R.drawable.evento
        )
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Uma pequena descriçao..."
        )
    }
}