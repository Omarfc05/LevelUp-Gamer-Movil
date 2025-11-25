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
import androidx.compose.ui.text.style.TextAlign
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
import com.example.levelupgamer.viewmodel.AuthViewModel
import com.example.levelupgamer.viewmodel.CartViewModel
import com.example.levelupgamer.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun CatalogScreen(
    viewModel: ProductViewModel = viewModel(),
    apiViewModel: ApiProductViewModel = viewModel(),
    navController: NavController,
    cartViewModel: CartViewModel,
    authViewModel: AuthViewModel
) {
    val localProducts = viewModel.products
    val apiProducts by apiViewModel.apiProducts.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentUser by authViewModel.currentUser.collectAsState()
    val isAdmin = currentUser?.email == "admin@levelup.com"

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

            // Título sección local
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Nuestros productos",
                    color = WhiteText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            // Productos locales (con índice para poder editar)
            items(localProducts.size) { index ->
                val product = localProducts[index]
                LocalProductCard(
                    product = product,
                    onAdd = {
                        cartViewModel.add(product)
                        scope.launch {
                            snackbarHostState.showSnackbar("✅ ${product.name} agregado al carrito")
                        }
                    },
                    isAdmin = isAdmin,
                    onUpdateProduct = { newName, newPrice ->
                        viewModel.updateProduct(index, newName, newPrice)
                        scope.launch {
                            snackbarHostState.showSnackbar("✏️ Producto actualizado")
                        }
                    }
                )
            }

            // Título sección API
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "De nuestros proveedores",
                    color = WhiteText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
            }

            // Mensaje de carga de API
            if (apiProducts.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "Cargando productos de proveedores...",
                        color = LightGrayText,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            } else {
                // Productos desde la API (solo vista)
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
    onAdd: () -> Unit,
    isAdmin: Boolean,
    onUpdateProduct: (String, String) -> Unit
) {
    var showEdit by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(product.name) }
    var editedPrice by remember { mutableStateOf(product.price) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
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

            Column {
                Button(
                    onClick = onAdd,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
                ) {
                    Text("Agregar", color = BlackBackground, fontWeight = FontWeight.Bold)
                }

                if (isAdmin) {
                    TextButton(
                        onClick = {
                            errorMessage = null
                            editedName = product.name
                            editedPrice = product.price
                            showEdit = true
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Editar", color = ElectricBlue, fontSize = 12.sp)
                    }
                }
            }
        }
    }

    if (showEdit) {
        AlertDialog(
            onDismissRequest = { showEdit = false },
            title = { Text("Editar producto") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedPrice,
                        onValueChange = { editedPrice = it },
                        label = { Text("Precio (solo números y puntos)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (errorMessage != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = errorMessage ?: "",
                            color = Color.Red,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        when {
                            editedName.isBlank() ->
                                errorMessage = "El nombre no puede estar vacío"
                            editedPrice.isBlank() ->
                                errorMessage = "El precio no puede estar vacío"
                            else -> {
                                errorMessage = null
                                onUpdateProduct(editedName, editedPrice)
                                showEdit = false
                            }
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEdit = false }) {
                    Text("Cancelar")
                }
            },
            containerColor = BlackBackground
        )
    }
}

@Composable
fun ApiProductCard(apiProduct: ApiProduct) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F0F0F))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = rememberAsyncImagePainter(apiProduct.thumbnail),
                contentDescription = apiProduct.title,
                modifier = Modifier.size(90.dp)
            )
            Text(
                text = apiProduct.title,
                color = WhiteText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Text(
                text = "Gratis",
                color = ElectricBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

