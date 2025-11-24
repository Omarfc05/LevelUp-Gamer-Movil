package com.example.levelupgamer.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.levelupgamer.model.ApiProduct
import com.example.levelupgamer.model.Product
import com.example.levelupgamer.ui.components.CatalogTopBar
import com.example.levelupgamer.ui.theme.*
import com.example.levelupgamer.viewmodel.ApiProductViewModel
import com.example.levelupgamer.viewmodel.CartViewModel
import com.example.levelupgamer.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun CatalogScreen(
    viewModel: ProductViewModel = viewModel(),
    apiViewModel: ApiProductViewModel = viewModel(),
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val localProducts = viewModel.products
    val apiProducts by apiViewModel.apiProducts.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { CatalogTopBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BlackBackground
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(BlackBackground)
        ) {

            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Nuestros productos",
                    color = WhiteText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(localProducts) { product ->
                LocalProductCard(
                    product = product,
                    onAdd = {
                        cartViewModel.add(product)
                        scope.launch {
                            snackbarHostState.showSnackbar("✅ ${product.name} agregado al carrito")
                        }
                    }
                )
            }

            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "De nuestros proveedores",
                    color = WhiteText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
            }

            if (apiProducts.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "Cargando productos de proveedores...",
                        color = LightGrayText,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            } else {
                items(apiProducts) { apiProduct ->
                    ApiProductCard(apiProduct)
                }
            }

            item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun LocalProductCard(
    product: Product,
    onAdd: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    product.name,
                    color = WhiteText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    product.price + " CLP",
                    color = NeonGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onAdd,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
            ) {
                Text("Agregar", color = BlackBackground, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ApiProductCard(apiProduct: ApiProduct) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F0F0F))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = rememberAsyncImagePainter(apiProduct.image),
                contentDescription = apiProduct.title,
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = apiProduct.title,
                color = WhiteText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )

            Text(
                text = "${apiProduct.price} USD",
                color = ElectricBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
