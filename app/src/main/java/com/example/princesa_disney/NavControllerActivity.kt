package com.example.princesa_disney

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.princesa_disney.telas.TelaLogin

@Composable
fun AppNavegation() {
    // esse cara e nosso motorista
    val navController = rememberNavController()

    // a rota inicial da minha rota
    NavHost(navController = navController, startDestination="login"){
        //mapear a rota
        composable("login") {
            TelaLogin(
                aoClickEnterNavigation = {
                    navController.navigate("main")
                }
            )
        }

        // 4. Mapeando a rota "detalhes"
        composable("main") {
            TelaHome(
                aoClicarParaVoltar = {
                    // Dizemos ao motorista para remover a tela atual da  pilha e voltar
                    navController.popBackStack()
                }
            )
        }
    }
}