package com.example.levelupgamer

import com.example.levelupgamer.model.Product
import com.example.levelupgamer.viewmodel.CartViewModel
import com.example.levelupgamer.R
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CartViewModelTest {

    @Test
    fun agregarProductoLoDejaEnElCarrito() = runTest {
        val vm = CartViewModel()

        val producto = Product(
            name = "Teclado Mecánico RGB",
            price = "89990",
            imageRes = R.drawable.keyboard
        )

        vm.add(producto)

        assertEquals(1, vm.items.value.size)
        assertEquals("Teclado Mecánico RGB", vm.items.value.first().name)
    }

    @Test
    fun vaciarCarritoDejaListaVacia() = runTest {
        val vm = CartViewModel()

        val producto = Product(
            name = "Mouse Gamer",
            price = "49990",
            imageRes = R.drawable.mouse
        )

        vm.add(producto)
        vm.clear()

        assertEquals(0, vm.items.value.size)
    }

    @Test
    fun totalCLPCalculaBien() = runTest {
        val vm = CartViewModel()

        vm.add(Product("Teclado", "10000", R.drawable.keyboard))
        vm.add(Product("Mouse", "5000", R.drawable.mouse))

        assertEquals(15000, vm.totalCLP())
    }
}
