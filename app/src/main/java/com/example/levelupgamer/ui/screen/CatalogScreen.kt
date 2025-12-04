package com.example.levelupgamer.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.example.levelupgamer.R

@Composable
fun CatalogScreen(
    viewModel: ProductViewModel = viewModel(),
    apiViewModel: ApiProductViewModel = viewModel(),
    navController: NavController,
    cartViewModel: CartViewModel,
    authViewModel: AuthViewModel
) {
    // CAMBIO IMPORTANTE: Usamos collectAsState para que la lista se actualice sola al cambiar la BD
    val localProducts by viewModel.products.collectAsState()
    val apiProducts by apiViewModel.apiProducts.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentUser by authViewModel.currentUser.collectAsState()
    val isAdmin = currentUser?.email == "admin@levelup.com"

    Scaffold(
        topBar = { CatalogTopBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BlackBackground,
        // 👇 NUEVO: Botón flotante para agregar productos "mágicos"
        floatingActionButton = {
            if (isAdmin) { // O quita el if si quieres probarlo sin ser admin
                FloatingActionButton(
                    onClick = {
                        viewModel.addMysteryProduct()
                        scope.launch { snackbarHostState.showSnackbar("🎁 ¡Caja Misteriosa agregada!") }
                    },
                    containerColor = NeonGreen
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.Black)
                }
            }
        }
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
                    text = "Catálogo Exclusivo (Base de Datos)",
                    color = WhiteText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            // Productos locales
            items(localProducts) { product -> // <-- OJO: Ahora iteramos sobre los objetos directos
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
                        viewModel.updateProduct(product, newName, newPrice)
                        scope.launch {
                            snackbarHostState.showSnackbar("✏️ Producto actualizado")
                        }
                    },
                    onDeleteProduct = {
                        viewModel.deleteProduct(product)
                        scope.launch {
                            snackbarHostState.showSnackbar("🗑️ Producto eliminado")
                        }
                    }
                )
            }

            // Título sección API
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "De nuestros proveedores (API)",
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
                        text = "Cargando productos...",
                        color = LightGrayText,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            } else {
                items(apiProducts) { apiProduct ->
                    ApiProductCard(apiProduct)
                }
            }
            item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(60.dp)) } // Espacio para el FAB
        }
    }
}

@Composable
fun LocalProductCard(
    product: Product,
    onAdd: () -> Unit,
    isAdmin: Boolean,
    onUpdateProduct: (String, String) -> Unit,
    onDeleteProduct: () -> Unit // 👇 Nueva función para borrar
) {
    var showEdit by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(product.name) }
    var editedPrice by remember { mutableStateOf(product.price) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp), // Un poco más alto para los botones extra
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Imagen con ID de recurso seguro
                val image = if (product.imageRes != 0) product.imageRes else R.drawable.logo
                Image(
                    painter = painterResource(id = image),
                    contentDescription = product.name,
                    modifier = Modifier.size(70.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    product.name,
                    color = WhiteText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
                Text(
                    "$ " + product.price,
                    color = NeonGreen,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onAdd,
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Agregar", color = BlackBackground, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                if (isAdmin) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = {
                            editedName = product.name
                            editedPrice = product.price
                            showEdit = true
                        }) {
                            Text("Editar", color = ElectricBlue, fontSize = 11.sp)
                        }
                        // 👇 Botón de Borrar
                        IconButton(onClick = onDeleteProduct) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar",
                                tint = Color.Red,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // El Dialog de edición se mantiene igual, solo asegúrate de importarlo o copiarlo del anterior si lo necesitas completo
    if (showEdit) {
        AlertDialog(
            onDismissRequest = { showEdit = false },
            title = { Text("Editar producto") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Nombre") }
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedPrice,
                        onValueChange = { editedPrice = it },
                        label = { Text("Precio") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onUpdateProduct(editedName, editedPrice)
                    showEdit = false
                }) { Text("Guardar") }
            },
            dismissButton = {
                TextButton(onClick = { showEdit = false }) { Text("Cancelar") }
            }
        )
    }
}

// ApiProductCard sigue igual...
@Composable
fun ApiProductCard(apiProduct: ApiProduct) {
    // ... (mismo código de antes)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
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
                maxLines = 2,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Gratis (API)",
                color = ElectricBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}