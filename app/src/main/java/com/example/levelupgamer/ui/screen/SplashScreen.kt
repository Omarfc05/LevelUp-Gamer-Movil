package com.example.levelupgamer.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource // <-- Importante
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelupgamer.R // <-- Importa el "R" de tu proyecto
import com.example.levelupgamer.ui.theme.ElectricBlue
import com.example.levelupgamer.ui.theme.LightGrayText
import kotlinx.coroutines.delay
import com.example.levelupgamer.ui.theme.Orbitron


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("login")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de LevelUp Gamer",
                modifier = Modifier.size(120.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))


            Text("💻 LevelUp - Gamer", fontSize = 30.sp, color = ElectricBlue, fontFamily = Orbitron)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Cargando...", fontSize = 16.sp, color = LightGrayText)
        }
    }

}