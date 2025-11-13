package com.example.levelupgamer.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class CatalogScreenTest {
    @get: Rule
    val composableTestRule = createComposeRule()

    @Test
    fun catalogoMuestraTextoBarra(){
        composableTestRule.setContent {
            CatalogScreen(navController = rememberNavController())
        }
        composableTestRule.onNodeWithText("Catálogo")
    }

    @Test
    fun catalogoMuestraIconPerfil(){
        composableTestRule.setContent {
            CatalogScreen(navController = rememberNavController())
        }
        composableTestRule.onNodeWithContentDescription("Perfil")
    }


}