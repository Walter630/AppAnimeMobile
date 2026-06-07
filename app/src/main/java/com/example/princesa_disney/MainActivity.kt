package com.example.princesa_disney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.princesa_disney.entity.Anime
import com.example.princesa_disney.ui.theme.Princesa_DisneyTheme
import com.example.princesa_disney.view.HomeViewModel

// ── Paleta de cores ───────────────────────────────────────
private val DarkBg       = Color(0xFF0A0A1A)
private val CardBg       = Color(0xFF16213E)
private val Purple       = Color(0xFF9333EA)
private val PurpleLight  = Color(0xFFAB5CF7)
private val Pink         = Color(0xFFEC4899)
private val TextPrimary  = Color(0xFFF9FAFB)
private val TextMuted    = Color(0xFF9CA3AF)
private val Red          = Color(0xFFEF4444)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Princesa_DisneyTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = DarkBg) {
                    AppNavegation()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(
    aoClicarParaVoltar: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val animeList by viewModel.animeList.collectAsState()
    val mensagem by viewModel.mensagem.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var mostrarDialogAdicionar by remember { mutableStateOf(false) }
    var animeParaEditar by remember { mutableStateOf<Anime?>(null) }

    LaunchedEffect(mensagem) {
        mensagem?.let { snackbarHostState.showSnackbar(it); viewModel.limparMensagem() }
    }

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🎌 ", fontSize = 22.sp)
                        Text(
                            "AnimeCo",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardBg),
                actions = {
                    IconButton(onClick = aoClicarParaVoltar) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Sair", tint = Purple)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = CardBg) {
                val navColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Purple,
                    selectedTextColor = Purple,
                    indicatorColor = Purple.copy(alpha = 0.12f),
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, "Início") },
                    label = { Text("Início") }, selected = true, onClick = {}, colors = navColors
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, "Favoritos") },
                    label = { Text("Favoritos") }, selected = false, onClick = {}, colors = navColors
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AccountCircle, "Perfil") },
                    label = { Text("Perfil") }, selected = false, onClick = {}, colors = navColors
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialogAdicionar = true },
                containerColor = Purple,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        if (animeList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎌", fontSize = 64.sp)
                    Spacer(Modifier.height(16.dp))
                    Text("Nenhum anime ainda", color = TextMuted, fontSize = 16.sp)
                    Text("Toque no + para adicionar", color = TextMuted, fontSize = 13.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(animeList, key = { it.id }) { anime ->
                    CardAnime(
                        anime = anime,
                        onEditar = { animeParaEditar = it },
                        onDeletar = { viewModel.deletarAnime(it.id) }
                    )
                }
            }
        }
    }

    if (mostrarDialogAdicionar) {
        DialogAddEditAnime(
            anime = null,
            onSalvar = { nome, descricao, episodios, favorito, imageUrl ->
                viewModel.inserirAnime(nome, descricao, episodios, favorito, imageUrl)
                mostrarDialogAdicionar = false
            },
            onFechar = { mostrarDialogAdicionar = false }
        )
    }

    animeParaEditar?.let { anime ->
        DialogAddEditAnime(
            anime = anime,
            onSalvar = { nome, descricao, episodios, favorito, imageUrl ->
                viewModel.atualizarAnime(
                    anime.copy(name = nome, description = descricao, epsodios = episodios, favorite = favorito, imageUrl = imageUrl)
                )
                animeParaEditar = null
            },
            onFechar = { animeParaEditar = null }
        )
    }
}

// ── Card do Anime ─────────────────────────────────────────
@Composable
fun CardAnime(anime: Anime, onEditar: (Anime) -> Unit, onDeletar: (Anime) -> Unit) {
    var mostrarConfirmacao by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        // ── Imagem com overlay ──────────────────────────
        Box(
            modifier = Modifier.fillMaxWidth().height(210.dp)
        ) {
            if (anime.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = anime.imageUrl,
                    contentDescription = anime.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder com inicial do nome
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.linearGradient(listOf(Color(0xFF4A148C), Color(0xFF1565C0)))
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = anime.name.first().toString(),
                        fontSize = 90.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White.copy(alpha = 0.15f)
                    )
                }
            }

            // Gradiente escurecendo de baixo
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.55f to Color.Transparent,
                        1f to CardBg
                    )
                )
            )

            // Ícone de favorito no canto superior direito
            if (anime.favorite) {
                Box(
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
                        .size(34.dp).background(Color(0x99000000), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Favorite, "Favorito", tint = Pink, modifier = Modifier.size(18.dp))
                }
            }
        }

        // ── Conteúdo ────────────────────────────────────
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = anime.name,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${anime.epsodios} ep",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PurpleLight
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = anime.description,
                fontSize = 13.sp,
                color = TextMuted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Botão editar
                IconButton(
                    onClick = { onEditar(anime) },
                    modifier = Modifier.size(38.dp).background(Purple.copy(alpha = 0.12f), CircleShape)
                ) {
                    Icon(Icons.Default.Edit, "Editar", tint = Purple, modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.width(10.dp))
                // Botão remover
                IconButton(
                    onClick = { mostrarConfirmacao = true },
                    modifier = Modifier.size(38.dp).background(Red.copy(alpha = 0.12f), CircleShape)
                ) {
                    Icon(Icons.Default.Delete, "Remover", tint = Red, modifier = Modifier.size(18.dp))
                }
            }
        }
    }

    if (mostrarConfirmacao) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacao = false },
            containerColor = CardBg,
            title = { Text("Remover Anime", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = { Text("Deseja remover '${anime.name}'?", color = TextMuted) },
            confirmButton = {
                TextButton(onClick = { onDeletar(anime); mostrarConfirmacao = false }) {
                    Text("Remover", color = Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacao = false }) {
                    Text("Cancelar", color = Purple)
                }
            }
        )
    }
}

// ── Dialog Adicionar / Editar ─────────────────────────────
@Composable
fun DialogAddEditAnime(
    anime: Anime?,
    onSalvar: (nome: String, descricao: String, episodios: Int, favorito: Boolean, imageUrl: String) -> Unit,
    onFechar: () -> Unit
) {
    var nome by remember { mutableStateOf(anime?.name ?: "") }
    var descricao by remember { mutableStateOf(anime?.description ?: "") }
    var episodiosTexto by remember { mutableStateOf(anime?.epsodios?.toString() ?: "") }
    var favorito by remember { mutableStateOf(anime?.favorite ?: false) }
    var imageUrl by remember { mutableStateOf(anime?.imageUrl ?: "") }
    var erroEpisodios by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onFechar,
        containerColor = CardBg,
        title = {
            Text(
                if (anime == null) "➕ Adicionar Anime" else "✏️ Editar Anime",
                color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                // Preview da imagem
                if (imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Preview",
                        modifier = Modifier.fillMaxWidth().height(110.dp).clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                darkField("Nome", nome) { nome = it }
                darkField("Descrição", descricao, minLines = 2) { descricao = it }
                darkField(
                    "Episódios", episodiosTexto,
                    keyboardType = KeyboardType.Number,
                    isError = erroEpisodios,
                    errorMsg = "Número inválido"
                ) { episodiosTexto = it; erroEpisodios = false }
                darkField("URL da imagem (opcional)", imageUrl) { imageUrl = it }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = favorito, onCheckedChange = { favorito = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Purple, uncheckedColor = TextMuted
                        )
                    )
                    Text("Favorito", color = TextPrimary, fontSize = 14.sp)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val ep = episodiosTexto.toIntOrNull()
                if (nome.isBlank() || descricao.isBlank() || ep == null || ep <= 0) {
                    erroEpisodios = true; return@TextButton
                }
                onSalvar(nome.trim(), descricao.trim(), ep, favorito, imageUrl.trim())
            }) {
                Text("Salvar", color = Purple, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onFechar) {
                Text("Cancelar", color = TextMuted)
            }
        }
    )
}

// ── Campo de texto com tema dark ──────────────────────────
@Composable
fun darkField(
    label: String,
    value: String,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    errorMsg: String = "",
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        minLines = minLines,
        isError = isError,
        supportingText = if (isError) ({ Text(errorMsg, color = Red) }) else null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Purple,
            unfocusedBorderColor = Color(0xFF374151),
            focusedLabelColor = PurpleLight,
            unfocusedLabelColor = TextMuted,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = Purple
        )
    )
}