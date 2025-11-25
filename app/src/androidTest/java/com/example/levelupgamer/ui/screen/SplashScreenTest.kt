package com.example.levelupgamer.ui.screen



import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun splashMuestraTituloYCargando() {
        composeRule.setContent {
            val navController = rememberNavController()
            SplashScreen(navController = navController)
        }

        composeRule.onNodeWithText("💻 LevelUp - Gamer").assertExists()
        composeRule.onNodeWithText("Cargando...").assertExists()
    }
}
