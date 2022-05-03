package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

import com.example.roomapp.model.Product
import com.example.roomapp.model.User


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE prodName LIKE :prodName")
    suspend fun getprodName(prodName: String): Product?


}