package com.example.levelupgamer.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.levelupgamer.R
import com.example.levelupgamer.ui.theme.BlackBackground
import com.example.levelupgamer.ui.theme.ElectricBlue
import com.example.levelupgamer.ui.theme.NeonGreen
import com.example.levelupgamer.ui.theme.WhiteText
import com.example.levelupgamer.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var currentUser by remember { mutableStateOf(authViewModel.currentUser.value) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                authViewModel.updateProfilePhoto(it.toString())
                currentUser = authViewModel.currentUser.value
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Foto de perfil
            if (currentUser?.fotoPerfil != null) {
                Image(
                    painter = rememberAsyncImagePainter(currentUser?.fotoPerfil),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .clickable { imagePicker.launch("image/*") }
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.default_avatar),
                    contentDescription = "Foto de perfil por defecto",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .clickable { imagePicker.launch("image/*") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentUser?.nombre ?: "Usuario desconocido",
                color = WhiteText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = currentUser?.email ?: "",
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón cambiar foto
            Button(
                onClick = { imagePicker.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
            ) {
                Text("Cambiar foto", color = WhiteText)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón cerrar sesión
            Button(
                onClick = {
                    coroutineScope.launch {
                        authViewModel.logout()
                        navController.navigate("login") {
                            popUpTo("catalog") { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
            ) {
                Text("Cerrar sesión", color = BlackBackground)
            }
        }
    }
}
