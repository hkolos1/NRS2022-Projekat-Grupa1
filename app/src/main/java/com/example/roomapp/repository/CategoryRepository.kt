package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.CategoryDAO
import com.example.roomapp.model.Category
import com.example.roomapp.model.Product

class CategoryRepository(private val categoryDao: CategoryDAO) {

    val readAllData: LiveData<List<Category>> = categoryDao.readAllData()

    suspend fun addCategory(category: Category){
        categoryDao.addCategory(category)
    }

    suspend fun updateCategory(category: Category){
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category){
        categoryDao.deleteCategory(category)
    }

    suspend fun deleteAllCategories(){
        categoryDao.deleteAllCategories()
    }

}