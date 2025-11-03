package com.example.levelupgamer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelupgamer.ui.screen.*
import com.example.levelupgamer.data.local.AppDatabase
import com.example.levelupgamer.data.repository.CurrentUserRepository
import com.example.levelupgamer.data.repository.UserRepository
import com.example.levelupgamer.viewmodel.AuthViewModel
import com.example.levelupgamer.viewmodel.AuthViewModelFactory
import com.example.levelupgamer.viewmodel.ProductViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userRepo = UserRepository(db.userDao())
    val currentUserRepo = CurrentUserRepository(db.currentUserDao())
    val authFactory = AuthViewModelFactory(userRepo, currentUserRepo)

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("login") {
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("register") {
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)
            RegisterScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("catalog") {
            val productViewModel: ProductViewModel = viewModel()
            CatalogScreen(viewModel = productViewModel, navController = navController)
        }
        composable("profile") {
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)
            ProfileScreen(authViewModel = authViewModel, navController = navController)
        }
    }
}
