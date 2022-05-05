package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Product
import com.example.roomapp.model.User


@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM product_table WHERE branchId = :id")
    fun getProductsFromBranch(id: Int): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE deliveryStatus = :status")
    fun getProductsFromStatus(status: String): LiveData<List<Product>>

    @Query("DELETE FROM product_table")
    fun deleteAllProducts()

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<Product>
}