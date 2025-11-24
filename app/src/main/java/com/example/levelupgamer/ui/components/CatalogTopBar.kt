package com.example.levelupgamer.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelupgamer.ui.theme.ElectricBlue
import com.example.levelupgamer.ui.theme.WhiteText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "LevelUp - Gamer",
                color = WhiteText,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
        },
        actions = {
            IconButton(onClick = { navController.navigate("cart") }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Ir al carrito",
                    tint = WhiteText
                )
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Ir al perfil",
                    tint = WhiteText
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ElectricBlue,
            titleContentColor = WhiteText,
            actionIconContentColor = WhiteText
        )
    )
}
