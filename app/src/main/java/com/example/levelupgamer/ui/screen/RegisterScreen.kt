package com.example.levelupgamer.ui.screen

import android.net.Uri
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.levelupgamer.ui.theme.*
import com.example.levelupgamer.viewmodel.AuthState
import com.example.levelupgamer.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> fotoUri = uri }


    val isFormValid = remember(nombre, email, password) {
        nombre.isNotBlank() &&
                email.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.length >= 6
    }


    val animatedBgColor by animateColorAsState(
        targetValue = if (isFormValid && errorMessage == null)
            BlackBackground.copy(alpha = 0.9f)
        else
            BlackBackground,
        label = "formBackgroundColor"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedBgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Registro de Usuario",
                color = WhiteText,
                fontSize = 26.sp,
                style = MaterialTheme.typography.titleLarge
            )


            AnimatedVisibility(visible = isFormValid && errorMessage == null) {
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
                        text = "Formulario listo para enviar",
                        color = NeonGreen,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (fotoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(fotoUri),
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(120.dp)
                    )
                } else {
                    Text(
                        "Seleccionar foto",
                        color = ElectricBlue,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre", color = LightGrayText) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = LightGrayText) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = LightGrayText) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = LightGrayText,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                )
            )

            Spacer(Modifier.height(24.dp))

            // ▶️ Botón animado: se ilumina cuando es válido
            val buttonColor by animateColorAsState(
                targetValue = if (isFormValid && errorMessage == null)
                    NeonGreen
                else
                    ElectricBlue.copy(alpha = 0.6f),
                label = "registerButtonColor"
            )

            Button(
                onClick = {
                    when {
                        nombre.isBlank() -> errorMessage = "El nombre no puede estar vacío"
                        email.isBlank() -> errorMessage = "El correo no puede estar vacío"
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> errorMessage = "Correo no válido"
                        password.length < 6 -> errorMessage = "La contraseña debe tener al menos 6 caracteres"
                        else -> {
                            errorMessage = null
                            authViewModel.register(
                                name = nombre,
                                email = email,
                                password = password,
                                imageUri = fotoUri?.toString()
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    disabledContainerColor = ElectricBlue.copy(alpha = 0.4f)
                )
            ) {
                Text("Registrar", color = WhiteText)
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 12.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate("login") }) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = NeonGreen)
            }

            when (authState) {
                is AuthState.RegisterSuccess -> {
                    LaunchedEffect(Unit) {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                }
                is AuthState.Error -> {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 12.dp),
                        textAlign = TextAlign.Center
                    )
                }
                else -> {}
            }
        }
    }
}
