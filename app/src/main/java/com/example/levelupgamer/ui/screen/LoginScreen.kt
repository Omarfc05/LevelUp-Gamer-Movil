package com.example.levelupgamer.ui.screen

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelupgamer.R
import com.example.levelupgamer.ui.theme.BlackBackground
import com.example.levelupgamer.ui.theme.ElectricBlue
import com.example.levelupgamer.ui.theme.LightGrayText
import com.example.levelupgamer.ui.theme.NeonGreen
import com.example.levelupgamer.ui.theme.WhiteText
import com.example.levelupgamer.viewmodel.AuthState
import com.example.levelupgamer.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.collectAsState()

    // formulario válido
    val isFormValid = remember(email, password) {
        email.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.isNotBlank()
    }

    // fondo animado según validez
    val animatedBgColor by animateColorAsState(
        targetValue = if (isFormValid && localError == null) {
            BlackBackground.copy(alpha = 0.9f)
        } else {
            BlackBackground
        },
        label = "loginBackgroundColor"
    )

    // color del botón animado
    val buttonColor by animateColorAsState(
        targetValue = if (isFormValid && localError == null)
            NeonGreen
        else
            ElectricBlue.copy(alpha = 0.6f),
        label = "loginButtonColor"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedBgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
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

            // check animado cuando está listo
            AnimatedVisibility(visible = isFormValid && localError == null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Formulario válido",
                        tint = NeonGreen
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Datos listos para ingresar",
                        color = NeonGreen,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    localError = null
                },
                label = { Text("Email", color = LightGrayText) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    focusedLabelColor = ElectricBlue,
                    cursorColor = ElectricBlue,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    localError = null
                },
                label = { Text("Contraseña", color = LightGrayText) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    focusedLabelColor = ElectricBlue,
                    cursorColor = ElectricBlue,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        email.isBlank() -> localError = "El email no puede estar vacío"
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> localError = "Formato de correo no válido"
                        password.isBlank() -> localError = "La contraseña no puede estar vacía"
                        else -> {
                            localError = null
                            authViewModel.login(email, password)
                        }
                    }
                },
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    disabledContainerColor = ElectricBlue.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar", color = WhiteText)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("register") }) {
                Text("¿No tienes cuenta? Regístrate", color = NeonGreen)
            }

            // errores locales (validación básica)
            localError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // feedback del ViewModel
            when (authState) {
                is AuthState.Error -> {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                is AuthState.LoginSuccess -> {
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
