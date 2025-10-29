package com.example.levelupgamer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelupgamer.ui.screen.SplashScreen
import com.example.levelupgamer.ui.screen.LoginScreen
import com.example.levelupgamer.ui.screen.RegisterScreen
import com.example.levelupgamer.ui.screen.CatalogScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("catalog") { // 👈 ESTA RUTA DEBE EXISTIR
            CatalogScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("catalog") {
            CatalogScreen(navController)
        }


    }
}
