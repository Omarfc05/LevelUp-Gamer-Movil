package com.example.levelupgamer.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.model.Product
import com.example.levelupgamer.ui.components.CatalogTopBar
import com.example.levelupgamer.ui.theme.BlackBackground
import com.example.levelupgamer.ui.theme.NeonGreen
import com.example.levelupgamer.ui.theme.WhiteText
import com.example.levelupgamer.viewmodel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CatalogScreen(
    viewModel: ProductViewModel = viewModel(),
    navController: NavController
) {
    val products = viewModel.products
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
    ) {
        CatalogTopBar(navController)
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            items(products) { product ->
                ProductCard(product, scope)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, scope: CoroutineScope) {
    var pressed by remember { mutableStateOf(false) }

    val glow by animateFloatAsState(
        targetValue = if (pressed) 25f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "glowAnim"
    )

    val scale by animateFloatAsState(
        targetValue = if (pressed) 1.05f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "scaleAnim"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .graphicsLayer { shadowElevation = glow }
            .scale(scale)
            .clickable {
                pressed = true
                scope.launch {
                    delay(250)
                    pressed = false
                }
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111)),
        elevation = CardDefaults.cardElevation(defaultElevation = glow.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = product.name,
                color = WhiteText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.price + " CLP",
                color = NeonGreen,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
