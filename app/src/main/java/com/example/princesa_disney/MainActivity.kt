package com.example.princesa_disney


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.princesa_disney.ui.theme.Princesa_DisneyTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite

data class Anime(
    val titulo: String, val episodios: String, val descricao: String, val image: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Princesa_DisneyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavegation()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(aoClicarParaVoltar: () -> Unit) {
    val listaAnimes = listOf(
        Anime(
            "Naruto",
            "220 Episódios",
            "Um jovem ninja busca reconhecimento e o sonho de se tornar Hokage.",
            R.drawable.evento
        ), Anime(
            "Death Note",
            "37 Episódios",
            "Um estudante encontra um caderno que pode matar pessoas.",
            R.drawable.macabro
        ), Anime(
            "Jujutsu Kaisen",
            "47 Episódios",
            "Um garoto engole um dedo amaldiçoado para salvar seus amigos.",
            R.drawable.gojo
        )
    )

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Catalogo de animes") }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            actions = {
                IconButton(onClick = aoClicarParaVoltar) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Sair da conta",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            })
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
            NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Início"
                        )
                    },
                    label = { Text("Início") },
                    selected = true, // Diz se o botão está "aceso" ou não
                    onClick = {
                        // Ação ao clicar (ex: ir para a lista de animes)

                    })

                        // Botão 2: Favoritos
                NavigationBarItem (
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
                label = { Text("Favoritos") },
                selected = false, // Como estamos na tela Início, o Favoritos fica apagado
                onClick = {
                    // Ação ao clicar (ex: ir para a tela de favoritos)
                })
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Início"
                        )
                    },
                    label = { Text("Início") },
                    selected = true, // Diz se o botão está "aceso" ou não
                    onClick = {
                        // Ação ao clicar (ex: ir para a lista de animes)
                    })
        }
    }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            items(listaAnimes) { anime ->
                CardAnime(anime)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = aoClicarParaVoltar, modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sair")
                }
            }
        }
    }
}

@Composable
fun CardAnime(anime: Anime) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = anime.image),
                contentDescription = "Capa do anime ${anime.image}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = anime.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
            Text(
                text = anime.episodios,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = anime.descricao, fontSize = 14.sp
            )
        }
    }

    fun PegarListaAnime(): List<Anime>  {
        return listOf(
            Anime(
                "Naruto",
                "220 Episódios",
                "Um jovem ninja busca reconhecimento e o sonho de se tornar Hokage.",
                R.drawable.evento
            ), Anime(
                "Death Note",
                "37 Episódios",
                "Um estudante encontra um caderno que pode matar pessoas.",
                R.drawable.macabro
            ), Anime(
                "Jujutsu Kaisen",
                "47 Episódios",
                "Um garoto engole um dedo amaldiçoado para salvar seus amigos.",
                R.drawable.gojo
            )
        )
    }
}/* @Composable
 fun MyApp(modifier: Modifier = Modifier){
     Surface(
         modifier = modifier,
         color = MaterialTheme.colorScheme.background
     ) {
         Greeting("Android")
     }
 }

 @Composable
 fun Greeting(name: String, modifier: Modifier = Modifier) {
     Surface(color = MaterialTheme.colorScheme.primary) {
         Text(
             text = "Hello $name!",
             modifier = modifier.padding(24.dp) //usa o . para referenciar o tipo de pading
         )
     }
 }*/

//mostra o conteudo referente ao APP
@Preview(showBackground = true, name = "Text preview")
@Composable
fun DevsPreview() {
    Princesa_DisneyTheme {
        TelaHome(aoClicarParaVoltar = {})
    }
}
