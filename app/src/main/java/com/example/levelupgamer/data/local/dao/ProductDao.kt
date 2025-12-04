package com.example.levelupgamer.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.levelupgamer.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Obtener productos
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    // Insertar un producto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    // Actualizar un producto
    @Update
    suspend fun update(product: Product)
    //Borrar produto
    @Delete
    suspend fun delete(product: Product)
}
