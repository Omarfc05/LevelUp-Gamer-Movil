package com.example.levelupgamer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupgamer.ui.components.CatalogTopBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatalogTopBarTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun catalogoMuestraTextoBarra() {
        composeRule.setContent {
            CatalogTopBar(navController = rememberNavController())
        }

        composeRule.onNodeWithText("LevelUp | Catálogo").assertExists()
    }

    @Test
    fun catalogoMuestraIconPerfil() {
        composeRule.setContent {
            CatalogTopBar(navController = rememberNavController())
        }

        composeRule.onNodeWithContentDescription("Ir al perfil").assertExists()
    }
}
