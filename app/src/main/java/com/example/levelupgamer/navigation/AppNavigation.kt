package com.example.levelupgamer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelupgamer.data.local.AppDatabase
import com.example.levelupgamer.data.repository.CurrentUserRepository
import com.example.levelupgamer.data.repository.UserRepository
import com.example.levelupgamer.ui.screen.CartScreen
import com.example.levelupgamer.ui.screen.CatalogScreen
import com.example.levelupgamer.ui.screen.LoginScreen
import com.example.levelupgamer.ui.screen.ProfileScreen
import com.example.levelupgamer.ui.screen.RegisterScreen
import com.example.levelupgamer.ui.screen.SplashScreen
import com.example.levelupgamer.viewmodel.ApiProductViewModel
import com.example.levelupgamer.viewmodel.AuthViewModel
import com.example.levelupgamer.viewmodel.AuthViewModelFactory
import com.example.levelupgamer.viewmodel.CartViewModel
import com.example.levelupgamer.viewmodel.ProductViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current


    val db = AppDatabase.getDatabase(context)
    val userRepo = UserRepository(db.userDao())
    val currentUserRepo = CurrentUserRepository(db.currentUserDao())
    val authFactory = AuthViewModelFactory(userRepo, currentUserRepo)

    val cartViewModel: CartViewModel = viewModel()

    val apiProductViewModel: ApiProductViewModel = viewModel()

    val productViewModel: ProductViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(db.productDao()) as T
            }
        }
    )

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("register") {
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("catalog") {
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)
            CatalogScreen(
                viewModel = productViewModel,
                apiViewModel = apiProductViewModel,
                navController = navController,
                cartViewModel = cartViewModel,
                authViewModel = authViewModel
            )
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable("profile") {
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }

}
