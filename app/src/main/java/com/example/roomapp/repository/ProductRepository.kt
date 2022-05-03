package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.ProductDao
import com.example.roomapp.model.Product

class ProductRepository(private val productDao: ProductDao) {

    val readAllData: LiveData<List<Product>> = productDao.readAllData()


}