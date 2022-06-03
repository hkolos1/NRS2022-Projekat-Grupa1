package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.ProductDatabase
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import com.example.roomapp.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Product>>

    val repository: ProductRepository

    init {
        val productDao = ProductDatabase.getDatabase(
            application
        ).productDao()
        repository = ProductRepository(productDao)
        readAllData = repository.readAllData
    }
    fun addProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(product)
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product)
        }
    }

    fun deleteAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllProducts()
        }
    }

    fun getProductsFromBranch(id: Int): LiveData<List<Product>>{
        return repository.getProductsFromBranch(id)
    }

    fun getProductsFromStatus(status: String): LiveData<List<Product>>{
        return repository.getProductsFromStatus(status)
    }

    fun getAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllProducts()
        }
    }

    fun getProductById(id: Int): Product{
        return repository.getProductById(id)
    }
}