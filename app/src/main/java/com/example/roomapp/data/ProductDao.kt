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

    @Transaction
    @Query("SELECT * FROM product_table WHERE branchId = :id")
    fun getProductsFromBranch(id: Int): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE deliveryStatus = :status")
    fun getProductsFromStatus(status: String): LiveData<List<Product>>

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product_table WHERE id = :id")
    fun getProductById(id: Int): Product
}