package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
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
}