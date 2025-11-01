package com.example.levelupgamer.ui.components

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Catálogo",
                color = Color.White
            )
        },
        actions = {
            IconButton(
                onClick = { navController.navigate("profile") }
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1E90FF) // Azul eléctrico
        )
    )
}
