package com.example.levelupgamer.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.levelupgamer.ui.theme.*
import com.example.levelupgamer.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUser.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text("Perfil del Usuario", color = WhiteText, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(32.dp))

            if (currentUser?.profileImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(currentUser?.profileImageUri),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(ElectricBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sin Foto", color = WhiteText)
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(text = "Nombre: ${currentUser?.name ?: "—"}", color = LightGrayText)
            Text(text = "Email: ${currentUser?.email ?: "—"}", color = LightGrayText)

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { authViewModel.logout() },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
            ) {
                Text("Cerrar Sesión", color = WhiteText)
            }
        }
    }
}
