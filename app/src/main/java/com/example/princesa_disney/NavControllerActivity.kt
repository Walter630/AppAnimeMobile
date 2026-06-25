package com.example.princesa_disney

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavegation() {
    // esse cara e nosso motorista
    val navController = rememberNavController()

    // a rota inicial da minha rota
    NavHost(navController = navController, startDestination="login"){
        //mapear a rota
        composable("login") {
            TelaLogin(
                aoLogar = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 4. Mapeando a rota "detalhes"
        composable("main") {
            TelaHome(
                navController = navController,
                aoClicarParaVoltar = {
                    navController.navigate("login")  {
                        popUpTo("main") {inclusive = true}
                    }
                }
            )
        }

        composable("descobrir") { TelaDescobrir() }
    }
}
