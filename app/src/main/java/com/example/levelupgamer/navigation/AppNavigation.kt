package com.example.levelupgamer.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelupgamer.ui.screen.CatalogScreen
import com.example.levelupgamer.ui.screen.LoginScreen
import com.example.levelupgamer.ui.screen.ProfileScreen
import com.example.levelupgamer.ui.screen.RegisterScreen
import com.example.levelupgamer.ui.screen.SplashScreen
import com.example.levelupgamer.viewmodel.ProductViewModel

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
        composable("catalog") {
            val productViewModel: ProductViewModel = viewModel()
            CatalogScreen(
                viewModel = productViewModel,
                navController = navController
            )
        }
        composable("profile") {
            ProfileScreen()
        }
    }
}
