package com.example.levelupgamer.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamer.R
import com.example.levelupgamer.ui.theme.*
import com.example.levelupgamer.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            // 🔹 Logo superior
            Image(
                painter = painterResource(id = R.drawable.logologin),
                contentDescription = "Logo LevelUpGamer",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Inicia Sesión",
                style = MaterialTheme.typography.titleLarge,
                color = ElectricBlue
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = LightGrayText) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    focusedLabelColor = ElectricBlue,
                    cursorColor = ElectricBlue
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = LightGrayText) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    focusedLabelColor = ElectricBlue,
                    cursorColor = ElectricBlue
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { authViewModel.login(email, password) },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar", color = WhiteText)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("register") }) {
                Text("¿No tienes cuenta? Regístrate", color = NeonGreen)
            }

            // 🔹 Feedback de login
            when (authState) {
                is com.example.levelupgamer.viewmodel.AuthState.Error -> {
                    Text(
                        text = (authState as com.example.levelupgamer.viewmodel.AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is com.example.levelupgamer.viewmodel.AuthState.LoginSuccess -> {
                    LaunchedEffect(Unit) {
                        navController.navigate("catalog") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
