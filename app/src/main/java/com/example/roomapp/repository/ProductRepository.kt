package com.example.roomapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.roomapp.data.ProductDao
import com.example.roomapp.model.Product
import com.example.roomapp.model.User

class ProductRepository(private val productDao: ProductDao) {

    val readAllData: LiveData<List<Product>> = productDao.readAllData()

    suspend fun addProduct(product: Product){
        productDao.addProduct(product)
    }

    suspend fun updateProduct(product: Product){
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product){
        productDao.deleteProduct(product)
    }

    suspend fun deleteAllProducts(){
        productDao.deleteAllProducts()
    }

    suspend fun getprodName(prodName: String): Product?{
        Log.i("MYTAG", "inside Repository Getusers fun ")
        return productDao.getprodName(prodName)
    }

    fun getProductsFromBranch(id: Int): LiveData<List<Product>>{
        return productDao.getProductsFromBranch(id)
    }

    fun getProductsFromStatus(status: String): LiveData<List<Product>>{
        return productDao.getProductsFromStatus(status)
    }

    suspend fun getAllProducts(): List<Product>{
        return productDao.getAllProducts()
    }

    fun getProductById(id: Int): Product{
        return productDao.getProductById(id)
    }

}