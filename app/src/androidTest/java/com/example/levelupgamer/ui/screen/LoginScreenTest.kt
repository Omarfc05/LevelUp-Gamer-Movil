package com.example.levelupgamer.ui.screen



import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get: Rule
    val composableTestRule = createComposeRule()

    @Test
    fun loginMuestraImagen(){
        composableTestRule.setContent {
            CatalogScreen(navController = rememberNavController())
        }
        composableTestRule.onNodeWithContentDescription("Logo LevelUpGamer")
    }
    @Test
    fun muestraTextoBtnRegistrarse(){
        composableTestRule.setContent {
            CatalogScreen(navController = rememberNavController())
        }
        composableTestRule.onNodeWithText("¿No tienes cuenta? Regístrate")
    }
}