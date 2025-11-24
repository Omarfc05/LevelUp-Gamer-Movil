package com.example.levelupgamer.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelupgamer.ui.theme.*
import com.example.levelupgamer.viewmodel.CartViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val scope = rememberCoroutineScope()
    val items by cartViewModel.items.collectAsState()
    val total = cartViewModel.totalCLP()

    var pagando by remember { mutableStateOf(false) }
    var pagoOk by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Carrito",
                color = WhiteText,
                fontSize = 22.sp,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = { navController.navigate("catalog") },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
            ) {
                Text("Volver", color = WhiteText)
            }
        }

        Spacer(Modifier.height(12.dp))

        if (items.isEmpty() && !pagando && !pagoOk) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tu carrito está vacío", color = LightGrayText, fontSize = 18.sp)
            }
        } else {

            AnimatedVisibility(
                visible = pagando,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = NeonGreen)
                        Spacer(Modifier.height(12.dp))
                        Text("Procesando pago...", color = WhiteText, fontSize = 18.sp)
                    }
                }
            }

            AnimatedVisibility(
                visible = pagoOk,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✅ Pago exitoso", color = NeonGreen, fontSize = 22.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Gracias por tu compra!", color = WhiteText, fontSize = 16.sp)
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = {
                                pagoOk = false
                                navController.navigate("catalog")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                        ) {
                            Text("Volver al catálogo", color = WhiteText)
                        }
                    }
                }
            }

            if (!pagando && !pagoOk && items.isNotEmpty()) {

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items) { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF111111))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = product.imageRes),
                                contentDescription = product.name,
                                modifier = Modifier.size(50.dp)
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(product.name, color = WhiteText, fontSize = 14.sp)
                                Text("${product.price} CLP", color = NeonGreen, fontSize = 14.sp)
                            }

                            Button(
                                onClick = { cartViewModel.remove(product) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Quitar", color = WhiteText)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Total: $total CLP",
                    color = WhiteText,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { cartViewModel.clear() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                    ) {
                        Text("Vaciar", color = WhiteText)
                    }

                    Button(
                        onClick = {
                            if (total > 0) {
                                pagando = true
                                scope.launch {
                                    delay(2000)
                                    pagando = false
                                    pagoOk = true
                                    cartViewModel.clear()
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
                    ) {
                        Text("Pagar", color = BlackBackground)
                    }
                }
            }
        }
    }
}
