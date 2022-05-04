package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.ProductDao
import com.example.roomapp.model.Product
import com.example.roomapp.model.User

class ProductRepository(private val productDao: ProductDao) {

    val readAllData: LiveData<List<Product>> = productDao.readAllData()

    fun getProductsFromBranch(id: Int): LiveData<List<Product>>{
        return productDao.getProductsFromBranch(id)
    }

    fun getProductsFromStatus(status: String): LiveData<List<Product>>{
        return productDao.getProductsFromStatus(status)
    }

    fun deleteAllProducts(){
        productDao.deleteAllProducts()
    }

    suspend fun updateProduct(product: Product){
        productDao.updateProduct(product)
    }

    suspend fun getAllProducts(): List<Product>{
        return productDao.getAllProducts()
    }

}