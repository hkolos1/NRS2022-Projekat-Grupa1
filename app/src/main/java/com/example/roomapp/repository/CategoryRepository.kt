package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.CategoryDAO
import com.example.roomapp.model.Category

class CategoryRepository(private val categoryDao: CategoryDAO) {

    val readAllData: LiveData<List<Category>> = categoryDao.readAllData()

    suspend fun addCategory(category: Category){
        categoryDao.addCategory(category)
    }

    suspend fun updateCategory(category: Category){
        categoryDao.updateCategory(category)
    }

}