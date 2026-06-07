package com.example.princesa_disney.telas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.princesa_disney.AppNavegation
import com.example.princesa_disney.ui.theme.Princesa_DisneyTheme

// Paleta de cores para manter o padrão da sua Home
private val DarkBg = Color(0xFF0A0A1A)
private val Purple = Color(0xFF9333EA)
private val PurpleLight = Color(0xFFAB5CF7)
private val TextPrimary = Color(0xFFF9FAFB)
private val TextMuted = Color(0xFF9CA3AF)

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Princesa_DisneyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkBg // Usa o fundo escuro por padrão
                ) {
                    AppNavegation()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(aoClickEnterNavigation: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // Controle do "olhinho" da senha

    // Surface garantindo o fundo escuro para a tela toda
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp), // Mais espaço nas laterais para não grudar na borda
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ── Header (Logo e Título) ───────────────────────
            Text(text = "🎌", fontSize = 72.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "AnimeCo",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )
            Text(
                text = "Faça login para gerenciar seus animes",
                fontSize = 14.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 4.dp, bottom = 48.dp)
            )

            // ── Inputs ────────────────────────────────────────
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nome de usuário") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Usuário")
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Purple,
                    unfocusedBorderColor = Color(0xFF374151),
                    focusedLabelColor = PurpleLight,
                    unfocusedLabelColor = TextMuted,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    unfocusedLeadingIconColor = TextMuted,
                    focusedLeadingIconColor = Purple
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Senha") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Password, contentDescription = "Senha")
                },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Mostrar senha", tint = TextMuted)
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Purple,
                    unfocusedBorderColor = Color(0xFF374151),
                    focusedLabelColor = PurpleLight,
                    unfocusedLabelColor = TextMuted,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    unfocusedLeadingIconColor = TextMuted,
                    focusedLeadingIconColor = Purple
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ── Botão ─────────────────────────────────────────
            Button(
                onClick = aoClickEnterNavigation,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), // Deixa o botão mais "gordinho" e fácil de clicar
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Entrar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreviw() {
    Princesa_DisneyTheme {
        TelaLogin(aoClickEnterNavigation = {})
    }
}