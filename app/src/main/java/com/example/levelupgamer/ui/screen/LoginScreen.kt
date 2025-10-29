package com.example.levelupgamer.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelupgamer.ui.theme.*
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.example.levelupgamer.ui.theme.Orbitron




@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground),
        color = BlackBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🕹️ Título principal
            Text(
                text = "Level-Up Gamer",
                color = ElectricBlue,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Orbitron,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Inicia sesión para continuar",
                color = LightGrayText,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ✉️ Campo de correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = LightGrayText) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    cursorColor = ElectricBlue,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                ),
                        modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔒 Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = LightGrayText) },
                singleLine = true,
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    cursorColor = ElectricBlue,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                ),
                        modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔘 Botón Login
            Button(
                onClick = {
                    if (email == "admin@levelup.com" && password == "1234") {
                        navController.navigate("catalog")
                    } else {
                        // En caso de error, podrías mostrar un snackbar o mensaje
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricBlue,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("INICIAR SESIÓN", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🧩 Link de registro
            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = NeonGreen,
                    fontSize = 14.sp
                )
            }
        }
    }
}
