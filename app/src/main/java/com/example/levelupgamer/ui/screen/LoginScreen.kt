package com.example.levelupgamer.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {

    val email = loginViewModel.email
    val password = loginViewModel.password
    val loginSuccess = loginViewModel.loginSuccess

    // 🔹 Navegación segura al detectar éxito
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("catalog") {
                popUpTo("login") { inclusive = true } // evita volver atrás
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🎮 LevelUp Gamer",
                color = Color(0xFF1E90FF),
                fontSize = 28.sp
            )
            Text(
                text = "Inicia sesión para continuar",
                color = Color(0xFFD3D3D3),
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = email,
                onValueChange = { loginViewModel.onEmailChange(it) },
                label = { Text("Correo electrónico", color = Color.White) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                label = { Text("Contraseña", color = Color.White) },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { loginViewModel.login() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF39FF14)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión", color = Color.Black)
            }

            Button(
                onClick = { navController.navigate("register") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E90FF)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Cuenta", color = Color.White)
            }

            if (!loginSuccess && email.isNotEmpty() && password.isNotEmpty()) {
                Text(
                    text = "⚠️ Usuario o contraseña incorrectos",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }
    }
}
